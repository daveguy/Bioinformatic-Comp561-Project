package comp561Project;

import org.biojava.nbio.alignment.Alignments;
import org.biojava.nbio.alignment.SimpleGapPenalty;
import org.biojava.nbio.alignment.SubstitutionMatrixHelper;
import org.biojava.nbio.alignment.Alignments.PairwiseSequenceAlignerType;
import org.biojava.nbio.alignment.template.SequencePair;
import org.biojava.nbio.alignment.template.SubstitutionMatrix;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.AmbiguityDNACompoundSet;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;

public class LinearAligner {
	SubstitutionMatrix<NucleotideCompound> matrix;
	SimpleGapPenalty gapP;

	public LinearAligner(int openPenalty, int extensionPenalty){
		this.matrix = SubstitutionMatrixHelper.getNuc4_4();
		this.gapP = new SimpleGapPenalty(openPenalty, extensionPenalty);
	}
	
	public SequencePair<DNASequence, NucleotideCompound> getAlignment(String queryString, String targetString) throws CompoundNotFoundException{
		DNASequence query = new DNASequence(queryString,AmbiguityDNACompoundSet.getDNACompoundSet());
		DNASequence target = new DNASequence(targetString,AmbiguityDNACompoundSet.getDNACompoundSet());
		
		SequencePair<DNASequence, NucleotideCompound> psa =Alignments.getPairwiseAlignment(query, target,PairwiseSequenceAlignerType.LOCAL, gapP, matrix);
		return psa;
	}
}
