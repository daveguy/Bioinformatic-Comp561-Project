package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import sequenceCompressor.Compressor;

public class TestCompressor {
	Compressor compressor =  new Compressor();
	
	@Test
	public void test() {
		String s = "AAAAAACTTTGGGGGGGGGGGGTCCCCCACCTGGGGG";
		String sComp = "ACTGTCACTG";
		Map<Integer, String> set = new HashMap<>();
		set.put(1, s);
		
		assertEquals(sComp, Compressor.compress(set).get(1));
	}

}
