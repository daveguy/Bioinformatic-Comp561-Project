package tests;

import static org.junit.Assert.*;

import org.biojava.nbio.alignment.template.SequencePair;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;
import org.junit.Test;

import comp561Project.Classifier;
import comp561Project.LinearAligner;

public class TestClassifier {
	LinearAligner aligner = new LinearAligner(5, 2);
	
	@Test
	public void testAlignmentScore() throws CompoundNotFoundException {
		String targetSeq = "ACGAGTGACCGTGTTTCCCGCCTGGT";
		String querySeq =  "ACGAGTGCGTGTTTTCCCGCCTGGTCCC";
		SequencePair<DNASequence, NucleotideCompound> alignment = aligner.getAlignment(querySeq, targetSeq);
		System.out.println(alignment);
		assertEquals(84, Classifier.getAlignmentScore(alignment));
	}

}
