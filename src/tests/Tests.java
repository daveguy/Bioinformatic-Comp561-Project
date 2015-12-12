package tests;

import static org.junit.Assert.*;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;

import comp561Project.*;
import createStandard.CreateGoldStandard;

import org.junit.Test;

public class Tests {

	@Test
	public void test() {
		int threshold = 20;
		Map.Entry<Integer, int[]> read = new AbstractMap.SimpleEntry<Integer, int[]>(1, new int[]{10,110});
		Map.Entry<Integer, int[]> readCompare = new AbstractMap.SimpleEntry<Integer, int[]>(1, new int[]{50,200});
		Map.Entry<Integer, int[]> readOut = new AbstractMap.SimpleEntry<Integer, int[]>(1, new int[]{100,200});
		
		assertTrue(CreateGoldStandard.overlaps(read, readCompare, threshold));
		assertTrue(CreateGoldStandard.overlaps(readCompare, read, threshold));
		assertFalse(CreateGoldStandard.overlaps(read, readOut, threshold));
	}

}
