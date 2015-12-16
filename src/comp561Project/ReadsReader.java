package comp561Project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadsReader {
	public Map<Integer, String> loadReads(String filepath) throws IOException{
		Map<Integer, String> reads = new HashMap<Integer, String>();
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String line;
		int id = 1;
		while((line = reader.readLine()) != null){
			if(line.charAt(0) == '>'){//header, read next line
				line = reader.readLine();
				line.trim();
				line.toUpperCase();
			}
			reads.put(id, line);
			id++;
		}
		reader.close();
		return reads;
		
	}

}
