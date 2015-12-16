package analyze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AnalyzeResults {

	public static void main(String[] args) {

		final String GOLD_STANDARD_FILE = "files/goldStandard.txt";
		final String RESULTS_FILE = "files/results/compressedResults4.txt";
		final String PERFORMANCE_FILE = "files/performance/compressedPerformanceResults4.txt";

		Map<String, Double> headers = readHeader(RESULTS_FILE);
		Map<Integer, Integer[]> standard = readFiles(GOLD_STANDARD_FILE);
		Map<Integer, Integer[]> results = readFiles(RESULTS_FILE);
		
		ResultsAnalyzer analyzer = new ResultsAnalyzer();
		double[] precRec = analyzer.analyzeResults(standard, results);
		
		savePerformance(headers, precRec, PERFORMANCE_FILE);

	}

	private static void savePerformance(Map<String, Double> headers, double[] precRec, String filename) {
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
			writer.write(getOutputString(headers, precRec));
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static String getOutputString(Map<String, Double> headers, double[] precRec) {
		StringBuilder builder = new StringBuilder();
		builder.append("Total Time = ").append(headers.get("total")).append("\n");
		builder.append("Average Time = ").append(headers.get("average")).append("\n");
		builder.append("Gap Open Penalty = ").append(headers.get("openPenalty")).append("\n");
		builder.append("Gap Extension Penalty = ").append(headers.get("extensionPenalty")).append("\n");
		builder.append("Overlap Threshold = ").append(headers.get("overlapThreshold")).append("\n");
		builder.append("Alignment Score Threshold = ").append(headers.get("alignmentScoreThreshold")).append("\n");
		builder.append("Precision = ").append(precRec[0]).append("\n");
		builder.append("Recall = ").append(precRec[1]);
		
		return builder.toString();
	}

	public static Map<String, Double> readHeader(String filename){
		Map<String, Double> header = new HashMap<>();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
			String line;
			while((line = reader.readLine()) != null && line.startsWith("##")){
				line = line.substring(2);
				header.put(line.split("\t")[0], Double.parseDouble(line.split("\t")[1]));
			}
			reader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return header;
	}

	public static Map<Integer, Integer[]> readFiles(String filename){
		Map<Integer, Integer[]> results = new HashMap<>();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
			String line;
			while((line = reader.readLine())!= null){
				if(line.startsWith("##")){
					continue;
				}
				String[] lineSplit = line.split("\t");
				Integer id = Integer.parseInt(lineSplit[0]);
				Integer[] matches = new Integer[lineSplit.length - 1];
				for(int i = 1; i < lineSplit.length; i++){
					matches[i - 1] = Integer.parseInt(lineSplit[i]);
				}
				results.put(id, matches);
			}
			reader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return results;
	}

}
