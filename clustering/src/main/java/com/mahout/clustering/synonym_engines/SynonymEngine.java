package com.mahout.clustering.synonym_engines;

import net.didion.jwnl.data.POS;

public interface SynonymEngine {
	String[] getSynonyms(String s, POS pos);
	
	int getNumberSynsets();
	
	void setNumberSynsets(int numberSynsets);
}
