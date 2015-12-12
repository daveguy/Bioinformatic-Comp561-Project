package analyze;

import java.util.Map;
import java.util.Map.Entry;

public class ResultsAnalyzer {

	public double[] analyzeResults(Map<Integer, Integer[]> goldStandard, Map<Integer, Integer[]> results){
		
		double[] scores = new double[]{getPrecision(goldStandard, results), getRecall(goldStandard, results)};
		
		return scores;
	}
	
	private double getPrecision(Map<Integer, Integer[]> goldStandard, Map<Integer, Integer[]> results) {
		int positive = 0;
		int total = getNumMatches(results);
		for(Entry<Integer, Integer[]> entry : results.entrySet()){
			int id = entry.getKey();
			Integer[] standardMatches = goldStandard.get(id);
			for(Integer match : entry.getValue()){
				if(contains(standardMatches, match)){
					positive++;
				}
			}	
		}
		return (double)positive/total;
	}


	private double getRecall(Map<Integer, Integer[]> goldStandard, Map<Integer, Integer[]> results) {
			int positive = 0;
			int total = getNumMatches(goldStandard);
			for(Entry<Integer, Integer[]> entry : results.entrySet()){
				int id = entry.getKey();
				Integer[] standardMatches = goldStandard.get(id);
				for(Integer match : entry.getValue()){
					if(contains(standardMatches, match)){
						positive++;
					}
				}	
			}
		return (double)positive / total;
	}

	private int getNumMatches(Map<Integer, Integer[]> allMatches) {
		int count = 0;
		for(Entry<Integer, Integer[]> entry : allMatches.entrySet()){
			count += entry.getValue().length;
		}
		return count;
	}

	private boolean contains(Integer[] standardMatches, Integer match) {
		boolean contains = false;
		for(Integer i : standardMatches){
			if (i.equals(match)){
				contains = true;
			}
		}
		return contains;
	}
}
