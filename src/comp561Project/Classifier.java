package comp561Project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.biojava.nbio.alignment.template.SequencePair;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;

public class Classifier {
	static final String READS_FILE = "files/compressedReads.txt";
	static final String READS20_FILE = "files/compressedReads20.txt";
	static final int OPEN_PENALTY = 5;
	static final int EXTENSION_PENALTY = 2;
	static final int OVERLAP_THRESHOLD = 175;
	static final int MATCH_SCORE = 5;
	static final int MISMATCH_PENALTY = 4;
	static final String RESULTS_FILE = "files/results/compressedResults4.txt";
	static final double ALIGNMENT_SCORE_THRESHOLD = (OVERLAP_THRESHOLD * MATCH_SCORE) - (OVERLAP_THRESHOLD * .25 * OPEN_PENALTY);
//	static final double ALIGNMENT_SCORE_THRESHOLD = 475;
	public static void main(String[] args) throws CompoundNotFoundException{
		if(!changedInput()){
			System.out.println("Forgot to change the input you dink!");
			System.exit(0);
		}
		
		LinearAligner aligner = new LinearAligner(OPEN_PENALTY, EXTENSION_PENALTY);
		Map<Integer, String> reads = null;
		Map<Integer, String> reads20 = null;

		ReadsReader readsReader = new ReadsReader();
		try {
			reads = readsReader.loadReads(READS_FILE);
			reads20 = readsReader.loadReads(READS20_FILE);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<Integer, Integer[]> predictions = new HashMap<Integer, Integer[]>();
		int count = 0;
		long startTime = System.currentTimeMillis();
		for(Entry<Integer, String> entry : reads20.entrySet()){
			String read = entry.getValue();
			count++;
			List<Integer> matches = new ArrayList<>();
			String prefix;
			String suffix;
			if(read.length() > 250){
				prefix = read.substring(0,201);
				suffix = read.substring(read.length() - 201);
			}
			else{
				suffix = read;
				prefix = read;
			}
			for(Entry<Integer, String> entryToCompare : reads.entrySet()){
				if(!entry.getKey().equals(entryToCompare.getKey())){
					SequencePair<DNASequence, NucleotideCompound> prefixAlignment = aligner.getAlignment(prefix, entryToCompare.getValue());
					if(overlaps(prefixAlignment)){
						matches.add(entryToCompare.getKey());
					}
					else{
						SequencePair<DNASequence, NucleotideCompound> suffixAlignment = aligner.getAlignment(suffix, entryToCompare.getValue());
						if(overlaps(suffixAlignment)){
							matches.add(entryToCompare.getKey());
						}
					}
				}
			}

			System.out.println(count + " processed");
			predictions.put(entry.getKey(), matches.toArray(new Integer[matches.size()]));
		}
		long endTime = System.currentTimeMillis();
		long totalTime = (endTime - startTime) / 1000;
		System.out.println("Total runtime: " + totalTime);
		
		double averageTime = totalTime / (double)count;
		System.out.println("average time: " + averageTime);
		printResults(predictions, averageTime, totalTime);
		
	}

	private static boolean changedInput() {
		Scanner in = new Scanner(System.in);
		boolean changed = false;
		System.out.println("did you change the name?");
		String eh = in.nextLine();
		if(eh.equals("y") || eh.equals("yes")){
			changed = true;
		}
		in.close();
		return changed;
	}

	private static void printResults(Map<Integer, Integer[]> predictions,double averageTime, long totalTime) {
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(RESULTS_FILE)));
			writeHeader(writer, averageTime, totalTime);
			for(Entry<Integer, Integer[]> entry : predictions.entrySet()){
				StringBuilder builder = new StringBuilder();
				builder.append(entry.getKey()).append("\t");
				for(Integer match : entry.getValue()){
					builder.append(match).append("\t");
				}
				writer.write(builder.toString().trim());
				writer.newLine();
			}
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	
	private static void writeHeader(BufferedWriter writer, double averageTime, long totalTime) throws IOException{
		writer.write("##total\t" + totalTime);
		writer.newLine();
		writer.write("##average\t" + averageTime);
		writer.newLine();
		writer.write("##openPenalty\t" + OPEN_PENALTY);
		writer.newLine();
		writer.write("##extensionPenalty\t" + EXTENSION_PENALTY);
		writer.newLine();
		writer.write("##overlapThreshold\t" + OVERLAP_THRESHOLD);
		writer.newLine();
		writer.write("##alignmentScoreThreshold\t" + ALIGNMENT_SCORE_THRESHOLD);
		writer.newLine();
	}

	public static boolean overlaps(SequencePair<DNASequence, NucleotideCompound> alignment) {
		if(alignment.getLength() < OVERLAP_THRESHOLD){
			return false;
		}
		if(getAlignmentScore(alignment) < ALIGNMENT_SCORE_THRESHOLD){
			return false;
		}

		return true;
	}

	public static int getAlignmentScore(SequencePair<DNASequence, NucleotideCompound> alignment) {
		int score = 0;
		boolean inGap = false;
		for(int i = 1; i <= alignment.getLength(); i++){
			if(alignment.getCompoundInQueryAt(i).equals(alignment.getCompoundInTargetAt(i))){//match
				score += MATCH_SCORE;
				if(inGap){
					inGap = false;
				}
			}
			else if(!alignment.getCompoundInQueryAt(i).toString().equals("-") && !alignment.getCompoundInTargetAt(i).toString().equals("-")){
				score -= MISMATCH_PENALTY;
				if(inGap){
					inGap = false;
				}
			}
			else{
				if(!inGap){
					score -= OPEN_PENALTY;
					inGap = true;
				}
				else{
					score -= EXTENSION_PENALTY;
				}
			}
		}

		return score;
	}
}










