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

import org.biojava.nbio.alignment.template.SequencePair;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;

public class Classifier {
	static final String READS_FILE = "files/Reads.txt";
	static final int OPEN_PENALTY = 5;
	static final int EXTENSION_PENALTY = 2;
	static final int OVERLAP_THRESHOLD = 30;
	static final String RESULTS_FILE = "files/results/testResults.txt";
	static final double ALIGNMENT_SCORE_THRESHOLD = (OVERLAP_THRESHOLD * 4) - (OVERLAP_THRESHOLD * .25 * OPEN_PENALTY);
	public static void main(String[] args) throws CompoundNotFoundException{
		LinearAligner aligner = new LinearAligner(OPEN_PENALTY, EXTENSION_PENALTY);
		Map<Integer, String> reads = null;
		
		ReadsReader readsReader = new ReadsReader();
		try {
			reads = readsReader.loadReads(READS_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<Integer, Integer[]> predictions = new HashMap<Integer, Integer[]>();
		int count = 0;
		for(Entry<Integer, String> entry : reads.entrySet()){
			count++;
			List<Integer> matches = new ArrayList<>();
			for(Entry<Integer, String> entryToCompare : reads.entrySet()){
				if(!entry.getKey().equals(entryToCompare.getKey())){
					SequencePair<DNASequence, NucleotideCompound> alignment = aligner.getAlignment(entry.getValue(), entryToCompare.getValue());
					if(overlaps(alignment)){
						matches.add(entryToCompare.getKey());
					}
				}
			}
			if(count%100==0){
				System.out.println(count + " processed");
			}
			predictions.put(entry.getKey(), matches.toArray(new Integer[matches.size()]));
		}
		
		printResults(predictions);
	}

	private static void printResults(Map<Integer, Integer[]> predictions) {
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(RESULTS_FILE)));
			for(Entry<Integer, Integer[]> entry : predictions.entrySet()){
				StringBuilder builder = new StringBuilder();
				builder.append(entry.getKey()).append("\t");
				for(Integer match : entry.getValue()){
					builder.append(match).append("\t");
				}
				writer.write(builder.toString().trim());
			}
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
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
				score += 4;
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










