package com.mahout.clustering.model;

import com.mahout.clustering.wordnet.SynsetsStrategy;

public class InputParams {
	public static final String STOP_WORDS_FILE = "stopwords_default";
	
	public static final boolean CONDITIONALITY = false;
	
	public static final boolean HYPERNYMS = false;
	
	public static final boolean MERONYMS = false;
	
	public static final int GENERALIZATION_LEVELS = 0;
	
	public static final int MAX_SYNONYMES = 1;
	
	public static final Stemer STEMER = Stemer.WORDNET;
	
	public static final SynsetsStrategy SYNSET_STRATEGY = SynsetsStrategy.INCLUDE_ALL;
}
