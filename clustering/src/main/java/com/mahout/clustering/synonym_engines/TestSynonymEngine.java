package com.mahout.clustering.synonym_engines;

import java.util.HashMap;

import net.didion.jwnl.data.POS;


public class TestSynonymEngine implements SynonymEngine{
	private static HashMap<String, String[]> map = new HashMap<String, String[]>();
	static {
		map.put("quick", new String[] { "fast", "speedy" });
		map.put("jumps", new String[] { "leaps", "hops" });
		map.put("over", new String[] { "above" });
		map.put("lazy", new String[] { "apathetic", "sluggish" });
		map.put("dog", new String[] { "canine", "pooch" });
	}

	public String[] getSynonyms(String s, POS pos) {
		return map.get(s);
	}

	@Override
	public int getNumberSynsets() {
		return 0;
	}

	@Override
	public void setNumberSynsets(int numberSynsets) {
		
	}
}
