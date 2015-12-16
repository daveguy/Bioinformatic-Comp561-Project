package sequenceCompressor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import comp561Project.ReadsReader;
public class Compressor {

	public static void main(String[] args) throws IOException {
		final String READS_FILE = "files/Reads.txt";
		final String OUTPUT_FILE = "files/compressedReads.txt";
		
		ReadsReader reader = new ReadsReader();
		
		Map<Integer, String> reads = reader.loadReads(READS_FILE);
		
		Map<Integer, String> compressedReads = compress(reads);
		write(compressedReads, OUTPUT_FILE);

	}


	private static void write(Map<Integer, String> compressedReads, String OUTPUT_FILE) {
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(OUTPUT_FILE)));
			for(Entry<Integer, String> entry : compressedReads.entrySet()){
				writer.write(">");
				writer.write(entry.getKey().toString());
				writer.newLine();
				writer.write(entry.getValue());
				writer.newLine();
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}


	public static Map<Integer, String> compress(Map<Integer, String> reads) {
		Map<Integer, String> compressedReads = new HashMap<Integer, String>();
		for(Entry<Integer, String> entry : reads.entrySet()){
			compressedReads.put(entry.getKey(), compress(entry.getValue()));
		}
		
		return compressedReads;
	}

	private static String compress(String value) {
		char last = value.charAt(0);
		StringBuilder builder = new StringBuilder();
		builder.append(last);
		for(int i = 1; i < value.length(); i++){
			if(!(value.charAt(i) == last)){
				builder.append(value.charAt(i));
				last = value.charAt(i);
			}
		}
		
		
		return builder.toString();
	}
	
	

}
