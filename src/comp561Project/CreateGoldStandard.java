package comp561Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.io.*;
import static org.biojava.nbio.ws.alignment.qblast.BlastAlignmentParameterEnum.ENTREZ_QUERY;
import static org.biojava.nbio.ws.alignment.qblast.BlastAlignmentParameterEnum.MAX_NUM_SEQ;
import org.biojava.nbio.ws.alignment.qblast.*;
import org.biojava.nbio.core.sequence.io.util.IOUtils;

public class CreateGoldStandard {

	public static void main(String[] args) {
		BlastReader blastReader = new BlastReader();
		String blastFolder = "C:/Users/daveguy/workspace/Comp561Project/files/blastOutput";
		String goldStandardLocation = "C:/Users/daveguy/workspace/Comp561Project/files/goldStandard.txt";
		final int OVERLAP_THRESHOLD = 20;
		
		//[querystart, queryend, seqstart, seqend, querylength]
		Map<Integer, int[]> blastResults = blastReader.loadBlastResults(blastFolder);
		
		Map<Integer, int[]> readLocationInGenome = new HashMap<Integer, int[]>();
		
		for(Entry<Integer, int[]> entry: blastResults.entrySet()){
			int trueStart = entry.getValue()[2] - entry.getValue()[0];
			int trueEnd = trueStart + entry.getValue()[4];
			readLocationInGenome.put(entry.getKey(), new int[]{trueStart, trueEnd});
		}
		
		Map<Integer, List<Integer>> idWithMatches = findAllMatches(readLocationInGenome, OVERLAP_THRESHOLD);
		saveMatches(idWithMatches, goldStandardLocation);
	}

	private static void saveMatches(Map<Integer, List<Integer>> idWithMatches, String dir) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir)));
			for(Entry<Integer, List<Integer>> entry : idWithMatches.entrySet()){
				writer.write(entry.getKey().toString());
				for(Integer id : entry.getValue()){
					writer.write("\t");
					writer.write(id.toString());
				}
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

	private static Map<Integer, List<Integer>> findAllMatches(Map<Integer, int[]> readLocationInGenome, int threshold) {
		Map<Integer, List<Integer>> matches = new HashMap<Integer, List<Integer>>();
		for(Entry<Integer, int[]> read : readLocationInGenome.entrySet()){
			for(Entry<Integer, int[]> readCompare : readLocationInGenome.entrySet()){
				if(read.getKey().intValue() != readCompare.getKey().intValue()){
					if(overlaps(read, readCompare, threshold)){
						if(matches.containsKey(read.getKey())){
							matches.get(read.getKey()).add(readCompare.getKey());
						}
						else{
							List<Integer> l = new ArrayList<Integer>();
							l.add(readCompare.getKey());
							matches.put(read.getKey(), l);
						}
					}
				}
			}
		}
		
		return matches;
	}

	public static boolean overlaps(Entry<Integer, int[]> read, Entry<Integer, int[]> readCompare, int threshold) {
		boolean overlaps = false;
		int rStart = read.getValue()[0];
		int rEnd = read.getValue()[1];
		int rCompStart = readCompare.getValue()[0];
		int rCompEnd = readCompare.getValue()[1];
		
		if((rCompEnd > rStart + threshold) && (rCompEnd < rEnd)){
			overlaps = true;
		}
		if((rCompStart > rStart) && (rCompStart < rEnd - threshold)){
			overlaps = true;
		}
		
		return overlaps;
	}

}








