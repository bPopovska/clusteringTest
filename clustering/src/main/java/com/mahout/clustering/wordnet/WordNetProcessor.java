package com.mahout.clustering.wordnet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.dictionary.Dictionary;

import com.mahout.clustering.utils.WordnetUtils;

public class WordNetProcessor {
	
	Dictionary wordnet = null;
	public WordNetProcessor(){
		try{
		JWNL.initialize(new FileInputStream("JWNLproperties.xml"));
		wordnet = Dictionary.getInstance();
		} catch(JWNLException exception){
			exception.printStackTrace();
		} catch(IOException exception){
			exception.printStackTrace();
		}
	}

	public Set<String> getAllWords(String word, POS pos, int numerLevels, boolean getHolonyms, boolean getEntailment, SynsetsStrategy strategy) {
		Set<String> result = new HashSet<String>();
		try {
			List<String> hypernims = WordnetUtils.returnHypernims(word, pos, numerLevels, strategy,
					wordnet);			
			result.addAll(hypernims);
			IndexWord iWord = WordnetUtils.returnIndexWord(word, pos, wordnet);
			if(iWord == null){
				return result;
			}
			if (iWord.getPOS() == POS.VERB) {
				List<String> entailments = WordnetUtils.returnEntailments(word, pos,
						strategy, wordnet);
				result.addAll(entailments);
			} else if (iWord.getPOS() == POS.NOUN) {
				List<String> holonyms = WordnetUtils.returnHolonyms(word, pos,
						wordnet);
				result.addAll(holonyms);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return result;
	}

}
