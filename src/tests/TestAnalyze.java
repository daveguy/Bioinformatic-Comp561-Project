package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import analyze.ResultsAnalyzer;

public class TestAnalyze {
	ResultsAnalyzer analyzer = new ResultsAnalyzer();
	
	@Test
	public void test() {
		Map<Integer, Integer[]> goldStandard = new HashMap<Integer, Integer[]>();
		goldStandard.put(1, new Integer[]{2,3,5,8});
		goldStandard.put(2, new Integer[]{1,4,8,9});
		goldStandard.put(3, new Integer[]{1,4,6,10});
		
		Map<Integer, Integer[]> results = new HashMap<Integer, Integer[]>();
		results.put(1, new Integer[]{2,3,5,8,6,9,10});
		results.put(2, new Integer[]{1,4,8,9,3,5,10});
		results.put(3, new Integer[]{1,6,4,10,3,5,8});
		
		System.out.println(Arrays.toString(analyzer.analyzeResults(goldStandard, results)));
	}

}
