package analyze;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ResultsAnalyzer {

	public double[] analyzeResults(Map<Integer, Integer[]> goldStandard, Map<Integer, Integer[]> results){
		
		double[] scores = new double[]{getPrecision(goldStandard, results), getRecall(goldStandard, results)};
		
		return scores;
	}
	
	private double getPrecision(Map<Integer, Integer[]> goldStandard, Map<Integer, Integer[]> results) {
		int positive = 0;
		int total = getNumMatches(results);
		for(Entry<Integer, Integer[]> resultsEntry : results.entrySet()){
			int id = resultsEntry.getKey();
			Integer[] standardMatches = goldStandard.get(id);
			for(Integer resultsMatch : resultsEntry.getValue()){
				if(contains(standardMatches, resultsMatch)){
					positive++;
				}
			}	
		}
		return (double)positive/total;
	}


	private double getRecall(Map<Integer, Integer[]> goldStandard, Map<Integer, Integer[]> results) {
			int positive = 0;
			int total = getNumMatches(goldStandard, results.keySet());
			for(Entry<Integer, Integer[]> resultsEntry : results.entrySet()){
				int id = resultsEntry.getKey();
				Integer[] standardMatches = goldStandard.get(id);
				for(Integer resultsMatch : resultsEntry.getValue()){
					if(contains(standardMatches, resultsMatch)){
						positive++;
					}
				}	
			}
		return (double)positive / total;
	}

	private int getNumMatches(Map<Integer, Integer[]> goldStandard, Set<Integer> keySet) {
		int total = 0;
		for(Integer id : keySet){
			total += goldStandard.get(id).length;
		}
		
		return total;
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
