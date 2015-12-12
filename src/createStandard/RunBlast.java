package createStandard;

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
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", changeDir + db + " -query " + file.getAbsolutePath() + " -out " + outDir + "Blast_" + file.getName()
															+ " -max_target_seqs 1 " + " -outfmt \"7 sseqid qstart qend sstart send qlen\"");
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
