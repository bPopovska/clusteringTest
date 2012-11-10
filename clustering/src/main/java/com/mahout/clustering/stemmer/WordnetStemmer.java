package com.mahout.clustering.stemmer;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.dictionary.Dictionary;
import net.didion.jwnl.dictionary.MorphologicalProcessor;

public class WordnetStemmer implements Steemer{
	private Dictionary dic;
	private MorphologicalProcessor morph;
	private boolean IsInitialized = false;  
	public Map<String, String> allWordsMaped = null;
	
	public WordnetStemmer() {
		allWordsMaped = new HashMap<String, String>();

		try {
			JWNL.initialize(new FileInputStream("JWNLproperties.xml"));
			dic = Dictionary.getInstance();
			morph = dic.getMorphologicalProcessor();
			IsInitialized = true;
		} catch (FileNotFoundException e) {
			System.out
					.println("Error initializing Stemmer: JWNLproperties.xml not found");
		} catch (JWNLException e) {
			System.out.println("Error initializing Stemmer: " + e.toString());
			e.printStackTrace();
		}
	}
	
	public String findStem(String word){
		String result = "";
		while(true){
			if(allWordsMaped.containsKey(word)){
				return allWordsMaped.get(word);
			}
			String stem = nextStem(word);
			if(stem == null){
				return null;
			}
			if(stem.equals(word)){
				result = stem;
				break;
			}
			if(!allWordsMaped.containsKey(word)){
				allWordsMaped.put(word, stem);
			}
			word = stem;
		}
		return result;
	}
	
	public String nextStem(String word){
		word = word.toLowerCase();
		if ( !IsInitialized )
			return word;
		if ( word == null ) return null;
		if ( morph == null ) morph = dic.getMorphologicalProcessor();
		
		IndexWord iWord;
		if(allWordsMaped.containsKey(word)){
			return allWordsMaped.get(word);
		}
		try{
			iWord = morph.lookupBaseForm(POS.VERB, word);
			if(iWord != null){
				allWordsMaped.put(word, iWord.getLemma().toString());
				return iWord.getLemma().toString();
			}
			iWord = morph.lookupBaseForm(POS.NOUN, word);
			if(iWord != null){
				allWordsMaped.put(word, iWord.getLemma().toString());
				return iWord.getLemma().toString();
			}
			iWord = morph.lookupBaseForm(POS.ADJECTIVE, word);
			if(iWord != null){
				allWordsMaped.put(word, iWord.getLemma().toString());
				return iWord.getLemma().toString();
			}
			iWord = morph.lookupBaseForm(POS.ADVERB, word);
			if(iWord != null){
				allWordsMaped.put(word, iWord.getLemma().toString());
				return iWord.getLemma().toString();
			}
		}catch(JWNLException exception){
			exception.printStackTrace();
		}		
		return word;
	}
	
	public static void main(String[] args) throws IOException{
		WordnetStemmer s = new WordnetStemmer();
		BufferedReader in = new BufferedReader(new FileReader("primer.txt"));
		StringTokenizer tokenizer = null;
		while(true){
			String line = in.readLine();
			if(line == null){
				break;
			}
			tokenizer = new StringTokenizer(line, " '");
			while (tokenizer.hasMoreTokens()) {
				System.out.print(s.findStem(tokenizer.nextToken().toLowerCase()) + " ");
			}
		}
	}
}
