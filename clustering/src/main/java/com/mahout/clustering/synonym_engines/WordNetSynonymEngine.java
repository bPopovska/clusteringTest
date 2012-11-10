package com.mahout.clustering.synonym_engines;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.dictionary.Dictionary;

import com.mahout.clustering.utils.WordnetUtils;

public class WordNetSynonymEngine implements SynonymEngine {
	int numberSynsets = -1;
	
	public String[] getSynonyms(String word, POS pos) {
		try {
			JWNL.initialize(new FileInputStream("JWNLproperties.xml"));
		} catch (JWNLException exception) {
			// log
			exception.printStackTrace();
		} catch (FileNotFoundException exception) {
			// log
			exception.printStackTrace();
		}

		Dictionary wordnet = Dictionary.getInstance();
		List<Synset> allSynsets = null;
		try {
			
			//all,  or just most likely one?
			
			allSynsets = WordnetUtils.getAllSynsets(pos, word, wordnet, numberSynsets);
		} catch (JWNLException exception) {
			//log
			exception.printStackTrace();
		}
		// get all synsets for a given word
		Set<String> synonyms = new HashSet<String>();
		for (Synset sense : allSynsets) {
			// get all words for a given sense
			synonyms.addAll(WordnetUtils.returnWords(sense));
		}
		if(synonyms.contains(word)){
			synonyms.remove(word);
		}
		String[] result = new String[synonyms.size()];
		int index = 0;
		for (String s:synonyms) {
			result[index++] = s;
		}
		return result;
	}
	
	public int getNumberSynsets() {
		return numberSynsets;
	}
	
	public void setNumberSynsets(int numberSynsets) {
		this.numberSynsets = numberSynsets;
	}

}
