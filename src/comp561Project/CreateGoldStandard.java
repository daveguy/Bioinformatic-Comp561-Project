package comp561Project;

import java.util.Map;
import java.io.*;
import static org.biojava.nbio.ws.alignment.qblast.BlastAlignmentParameterEnum.ENTREZ_QUERY;
import static org.biojava.nbio.ws.alignment.qblast.BlastAlignmentParameterEnum.MAX_NUM_SEQ;
import org.biojava.nbio.ws.alignment.qblast.*;
import org.biojava.nbio.core.sequence.io.util.IOUtils;

public class CreateGoldStandard {

	public static void main(String[] args) {
		String readsLocation = "files/Reads.txt";
//		String readsLocation = "files/singleRead.txt";
		Map<Integer, String> reads = null;
		ReadsReader rr = new ReadsReader();
		String BLAST_OUTPUT = "blastOutput.xml";
		String DATABASE = "refseq_genomic";
		String DELIMETER = "$$$";
		File f = new File(BLAST_OUTPUT);



		try{
			reads = rr.loadReads(readsLocation);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		int count = 0;

		for(Integer key : reads.keySet()){
			String read = reads.get(key);

			NCBIQBlastService service = new NCBIQBlastService();

			NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();
			props.setBlastProgram(BlastProgramEnum.blastn);
			props.setBlastDatabase(DATABASE);
			props.setAlignmentOption(ENTREZ_QUERY, "txid5661 [ORGN]");
			props.setAlignmentOption(MAX_NUM_SEQ, "1");
			props.setBlastWordSize(16);

			NCBIQBlastOutputProperties outProps = new NCBIQBlastOutputProperties();
			outProps.setAlignmentNumber(1);

			String rid = null;
			FileWriter writer = null;
			BufferedReader reader = null;


			try{
				rid = service.sendAlignmentRequest(read, props);
				long startTime = System.currentTimeMillis();

				while(!service.isReady(rid) && (System.currentTimeMillis() - startTime) < 900000){//give up after 15 minutes
					System.out.println("waiting...");
					Thread.sleep(5000);
				}

				InputStream in = service.getAlignmentResults(rid, outProps);
				reader = new BufferedReader(new InputStreamReader(in));
			
				System.out.println("Saving");
				count ++;
				writer = new FileWriter(f, true);
				writer.write(DELIMETER + key + DELIMETER);
				writer.write("\n");
				String line;
				while((line = reader.readLine()) != null){
					writer.write(line + "\n");
				}
				Thread.sleep(60000);


			}
			catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			finally{
				IOUtils.close(writer);
				IOUtils.close(reader);
				service.sendDeleteRequest(rid);
			}
		}
		System.out.println("Final count that worked: " + count);
	}

}
