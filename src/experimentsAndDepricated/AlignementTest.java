package experimentsAndDepricated;
 
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.biojava.nbio.alignment.Alignments;
import org.biojava.nbio.alignment.Alignments.PairwiseSequenceAlignerType;
import org.biojava.nbio.alignment.SimpleGapPenalty;
import org.biojava.nbio.alignment.SubstitutionMatrixHelper;
import org.biojava.nbio.alignment.template.SequencePair;
import org.biojava.nbio.alignment.template.SubstitutionMatrix;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.AmbiguityDNACompoundSet;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;
import comp561Project.*; 

public class AlignementTest {
 
	public static void main(String[] args) throws CompoundNotFoundException{
//		String targetSeq = "CACGTTTCTTGTGGCAGCTTAAGTTTGAATGTCATTTCTTCAATGGGACGGA"+
//		          "GCGGGTGCGGTTGCTGGAAAGATGCATCTATAACCAAGAGGAGTCCGTGCGCTTCGACAGC"+
//			  "GACGTGGGGGAGTACCGGGCGGTGACGGAGCTGGGGCGGCCTGATGCCGAGTACTGGAACA"+
//			  "GCCAGAAGGACCTCCTGGAGCAGAGGCGGGCCGCGGTGGACACCTACTGCAGACACAACTA"+ 
//			  "CGGGGTTGGTGAGAGCTTCACAGTGCAGCGGCGAG";
//		String targetSeq = "ACGAGTGACCGTGTTTCCCGCCTGGT";
//		DNASequence target = new DNASequence(targetSeq,
//				AmbiguityDNACompoundSet.getDNACompoundSet());
// 
//		String querySeq = "ACGAGTGCGTGTTTTCCCGCCTGGTCCCCAGGCCCCCTTTCCGTCCTCAGGAA"+
//			  "GACAGAGGAGGAGCCCCTCGGGCTGCAGGTGGTGGGCGTTGCGGCGGCGGCCGGTTAAGGT"+
//			  "TCCCAGTGCCCGCACCCGGCCCACGGGAGCCCCGGACTGGCGGCGTCACTGTCAGTGTCTT"+
//			  "CTCAGGAGGCCGCCTGTGTGACTGGATCGTTCGTGTCCCCACAGCACGTTTCTTGGAGTAC"+
//			  "TCTACGTCTGAGTGTCATTTCTTCAATGGGACGGAGCGGGTGCGGTTCCTGGACAGATACT"+
//			  "TCCATAACCAGGAGGAGAACGTGCGCTTCGACAGCGACGTGGGGGAGTTCCGGGCGGTGAC"+
//			  "GGAGCTGGGGCGGCCTGATGCCGAGTACTGGAACAGCCAGAAGGACATCCTGGAAGACGAG"+
//			  "CGGGCCGCGGTGGACACCTACTGCAGACACAACTACGGGGTTGTGAGAGCTTCACCGTGCA"+ 
//			  "GCGGCGAGACGCACTCGT";
//		String querySeq =  "ACGAGTGCGTGTTTTCCCGCCTGGTCCC";
//		DNASequence query = new DNASequence(querySeq,
//				AmbiguityDNACompoundSet.getDNACompoundSet());
		final String READS_FILE = "files/Reads.txt";
		ReadsReader reader = new ReadsReader();
		Map<Integer, String> reads = null;
		try {
			reads = reader.loadReads(READS_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String querySeq = reads.get(1).substring(0, 201);
		String tergetSeq = reads.get(2);
		DNASequence query = new DNASequence(querySeq,AmbiguityDNACompoundSet.getDNACompoundSet());
		DNASequence target = new DNASequence(tergetSeq,AmbiguityDNACompoundSet.getDNACompoundSet());
		SubstitutionMatrix<NucleotideCompound> matrix = SubstitutionMatrixHelper.getNuc4_4();
		
 
		SimpleGapPenalty gapP = new SimpleGapPenalty();
		gapP.setOpenPenalty((short)5);
		gapP.setExtensionPenalty((short)2);
 
		SequencePair<DNASequence, NucleotideCompound> psa =
				Alignments.getPairwiseAlignment(query, target,
						PairwiseSequenceAlignerType.LOCAL, gapP, matrix);
 
		System.out.println(psa.getLength());
		System.out.println(psa.getCompoundInQueryAt(9).toString().equals("-"));
		System.out.println(psa);
		
	}
 
}