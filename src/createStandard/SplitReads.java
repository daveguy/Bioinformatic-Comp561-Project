package createStandard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class SplitReads {

	public static void main(String[] args) {
		String readsLocation = "files/Reads.txt";
		String outLocation = "files/splitReads/";
		
		int readNumber = 1;
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(readsLocation));
			String header;
			while((header = reader.readLine()) != null){
				String read = reader.readLine().trim();
				BufferedWriter writer = new BufferedWriter(new FileWriter(outLocation + "read" + readNumber + ".txt"));
				writer.write(header);
				writer.newLine();
				writer.write(read);
				writer.close();
				readNumber++;
			}
			reader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

}
