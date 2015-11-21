package comp561Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunBlast {

	public static void main(String[] args) throws IOException {
		String db = "C:/Users/daveguy/Documents/Fall2015/Comp561/Project/BlastDb/Genome.txt";
		String queryDir = "C:/Users/daveguy/workspace/Comp561Project/files/splitReads/";
		String outDir = "C:/Users/daveguy/workspace/Comp561Project/files/blastOutput/";
		String changeDir = "cd \"C:/Program Files/NCBI/blast-2.2.31+/bin\" && blastn -db ";
		
		File reads = new File(queryDir);
		for(File file : reads.listFiles()){
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", changeDir + db + " -query " + file.getAbsolutePath() + " -out " + outDir + "Blast_" + file.getName());
			builder.redirectErrorStream(true);
			Process p = builder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while((line = reader.readLine()) != null){
				System.out.println(line);
			}
		}
	}

}
//ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd \"C:/Program Files/NCBI/blast-2.2.31+/bin\" && blastn -db " + db + " -query " + query + " -out " + results);
//builder.redirectErrorStream(true);
//Process p = builder.start();
//BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
//String line;
//while (true) {
//    line = r.readLine();
//    if (line == null) { break; }
//    System.out.println(line);
//}