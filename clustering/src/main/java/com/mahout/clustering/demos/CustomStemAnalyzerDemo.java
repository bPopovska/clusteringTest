package com.mahout.clustering.demos;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import net.didion.jwnl.dictionary.Dictionary;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.Version;

import com.mahout.clustering.analyzers.CustomStemAnalyzer;
import com.mahout.clustering.model.WordnetProperties;
import com.mahout.clustering.stemmer.Steemer;
import com.mahout.clustering.stemmer.WordnetStemmer;
import com.mahout.clustering.synonym_engines.SynonymEngine;
import com.mahout.clustering.synonym_engines.WordNetSynonymEngine;
import com.mahout.clustering.utils.Stopwords;

public class CustomStemAnalyzerDemo {
	
	public static final String STOP_WORDS_FILE = "stopwords_default";
	
	public static final boolean CONDITIONALITY = false;
	
	public static final boolean HYPERNYMS = false;
	
	public static final boolean MERONYMS = false;
	
	public static final int GENERALIZATION_LEVELS = 0;
	
	public static final int MAX_SYNONYMES = 0;
	

	public static void main(String[] args) throws IOException{
		
		
		
		// enum that determines the Lucene library version
		Version version = Version.LUCENE_34;
		
		// a set of stop words used by analyzer
		Set<String> stopWords = Stopwords.load(STOP_WORDS_FILE);
		
		// stemmer used in preprocessing
		Steemer stemmer = new WordnetStemmer();
		
		// wordnet dictionary used
		Dictionary wordnet = Dictionary.getInstance();
		
		//wordnet properties
		WordnetProperties wordNetProperties = new WordnetProperties();
		
		wordNetProperties.setAddConditionality(CONDITIONALITY);
		wordNetProperties.setAddHypernyms(HYPERNYMS);
		wordNetProperties.setAddMeronyms(MERONYMS);
		wordNetProperties.setGeneralizationLevels(GENERALIZATION_LEVELS);
		
		SynonymEngine engine = new WordNetSynonymEngine();
		
		engine.setNumberSynsets(MAX_SYNONYMES);
		
		
		CustomStemAnalyzer customSteamAnalyzer = new CustomStemAnalyzer(version, stopWords, engine, stemmer, wordnet, wordNetProperties);
		
		FileReader reader = new FileReader(new File("test.txt"));
		
		
		TokenStream tokenStream = customSteamAnalyzer.tokenStream(null, reader);
		
		CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
		OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
		
		while(tokenStream.incrementToken()){
			int startOffset = offsetAttribute.startOffset();
			int endOffset = offsetAttribute.endOffset();
			String term = charTermAttribute.toString();
			System.out.println(term + " " + startOffset + " " + endOffset);
		}
		
		tokenStream.end();
		tokenStream.close();
		
		
	}
	
	

}
