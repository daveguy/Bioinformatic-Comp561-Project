package comp561Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class BlastReader {

	public Map<Integer, int[]> loadBlastResults(String folder) {
		Map<Integer, int[]> results = new HashMap<Integer, int[]>();

		File blastFolder = new File(folder);
		int count = 0;
		for(File file : blastFolder.listFiles()){
			String filename = file.getName();
			int id = Integer.parseInt(filename.substring(filename.indexOf("read") + 4, filename.indexOf(".txt")));

			try{
				BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
				String line;
				while((line = reader.readLine()) != null){
					if(!line.startsWith("#") && line.contains("Leishmania_donovani,_chromosome_1")){
						count++;
						String[] splitLine = line.split("\t");
						results.put(id, new int[]{Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2]), Integer.parseInt(splitLine[3]), Integer.parseInt(splitLine[4]), Integer.parseInt(splitLine[5])});
						break;
					}
				}

			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
//		System.out.println("Matches with LDC: " + count);

		return results;
	}
}
