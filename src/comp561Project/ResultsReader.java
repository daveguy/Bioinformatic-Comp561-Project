package comp561Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ResultsReader {
	public Map<Integer, Integer[]> loadResults(String filename){
		Map<Integer, Integer[]> results = new HashMap<Integer, Integer[]>();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
			String line;
			while((line = reader.readLine()) != null){
				String[] lineSplit = line.split("\t");
				Integer[] matches = new Integer[lineSplit.length - 1];
				Integer id = Integer.parseInt(lineSplit[0]);
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
