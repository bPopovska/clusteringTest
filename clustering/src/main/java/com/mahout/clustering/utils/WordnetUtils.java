package com.mahout.clustering.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerUtils;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.list.PointerTargetNode;
import net.didion.jwnl.data.list.PointerTargetNodeList;
import net.didion.jwnl.data.list.PointerTargetTree;
import net.didion.jwnl.dictionary.Dictionary;
import net.didion.jwnl.dictionary.MorphologicalProcessor;

import com.mahout.clustering.wordnet.SynsetsStrategy;

@SuppressWarnings("all")
public class WordnetUtils {

	/**
	 * Finds all the synonym sets to which a word belongs
	 * 
	 * @param pos
	 *            an array containing the parts of speech to be included in the
	 *            synonym search
	 * @param word
	 *            the word for which the
	 * @param wordnet
	 *            a dictionary instance
	 * @param numberSynsest
	 *            the maximal number of synonyms to be returned. If this number
	 *            is negative, all the synonym sets are returned.
	 * @return a list of synonym sets
	 * @throws JWNLException
	 */
	public static List<Synset> getAllSynsets(POS pos, String word,
			Dictionary wordnet, int numberSynsest) throws JWNLException {
		List<Synset> result = new LinkedList<Synset>();
		
		// is POS is undefined, return the word
		if(pos == null){
			return result;
		}
		IndexWord indexWord = wordnet.getIndexWord(pos, word);
		
		//if no synsets found, return the word
		if(indexWord == null){
			return result;
		}
		
		result.addAll(Arrays.asList(indexWord.getSenses()));
		if (result.size() <= numberSynsest || numberSynsest < 0) {
			return result;
		}
		return result.subList(0, numberSynsest);
	}

	/**
	 * Finds the most used synonym sets to which a word belongs
	 * 
	 * @param word
	 * @param pos
	 * @param wordnet
	 * @return
	 * @throws JWNLException
	 */
	public static Synset getMostUsedSynset(String word, POS pos,
			Dictionary wordnet) throws JWNLException {
		IndexWord indexWord = returnIndexWord(word, pos, wordnet);
		if (indexWord == null) {
			return null;
		}
		//FIXME: is this ok?
		Synset synset = indexWord.getSense(1);
		return synset;
	}

	public static List<Synset> findSynsets(String word, POS pos,
			Dictionary wordnet, SynsetsStrategy strategy) throws JWNLException {
		List<Synset> result = new LinkedList<Synset>();
		if (strategy.equals(SynsetsStrategy.INCLUDE_ALL)) {
			result = getAllSynsets(pos, word, wordnet, 100);
		} else if (strategy.equals(SynsetsStrategy.INCLUDE_FIRST)) {
			result.add(getMostUsedSynset(word, pos, wordnet));
		}
		return result;
	}

	public static String printWords(Synset sense) {
		String result = "";
		Word[] allWords = sense.getWords();
		for (int i = 0; i < allWords.length; i++) {
			result += allWords[i].getLemma() + " ";
		}
		return result;
	}

	public static List<String> returnWords(Synset sense) {
		List<String> result = new LinkedList<String>();
		Word[] allWords = sense.getWords();
		for (int i = 0; i < allWords.length; i++) {
			result.add(allWords[i].getLemma());
		}
		return result;
	}

	public static IndexWord returnIndexWord(String word, POS pos,
			Dictionary wordnet) {
		MorphologicalProcessor morph = wordnet.getMorphologicalProcessor();
		IndexWord indexWord = null;
		IndexWord max = null;
		try {
			int maxSenseCount = -1;
			indexWord = morph.lookupBaseForm(pos, word);
			if (indexWord != null) {
				int senseCount = indexWord.getSenseCount();
				if (senseCount > maxSenseCount) {
					maxSenseCount = senseCount;
					max = indexWord;
				}
			}
		} catch (JWNLException exception) {

		}
		return max;
	}

	public static List<String> returnHypernims(String word, POS pos,
			int levels, SynsetsStrategy synStrategy, Dictionary wordnet)
			throws JWNLException {
		List<String> result = new LinkedList<String>();
		List<Synset> senses = findSynsets(word, pos, wordnet, synStrategy);
		for (Synset sense : senses) {
			PointerTargetTree related = PointerUtils.getInstance()
					.getHypernymTree(sense, levels);
			List<PointerTargetNodeList> asList = (List<PointerTargetNodeList>) related
					.toList();
			Iterator iter = asList.iterator();
			while (iter.hasNext()) {
				PointerTargetNodeList list = (PointerTargetNodeList) iter
						.next();
				Iterator iter2 = list.iterator();
				while (iter2.hasNext()) {
					PointerTargetNode node = (PointerTargetNode) iter2.next();
					result.addAll(WordnetUtils.returnWords(node.getSynset()));
				}
			}
		}
		return result;
	}

	public static List<String> returnHolonyms(String word, POS pos,
			Dictionary wordnet) throws JWNLException {
		List<String> result = new LinkedList<String>();
		Synset sense = getMostUsedSynset(word, pos, wordnet);
		PointerTargetNodeList related = PointerUtils.getInstance().getHolonyms(
				sense);
		Iterator iter = related.iterator();
		while (iter.hasNext()) {
			PointerTargetNode node = (PointerTargetNode) iter.next();
			result.addAll(WordnetUtils.returnWords(node.getSynset()));
		}
		return result;
	}

	public static List<String> returnMeronyms(String word, POS pos,
			 SynsetsStrategy strategy, Dictionary wordnet) throws JWNLException{
		List<String> result = new LinkedList<String>();
		List<Synset> senses = findSynsets(word, pos, wordnet, strategy);
		for(Synset sense:senses){
			PointerTargetNodeList related = PointerUtils.getInstance().getMeronyms(sense);
			Iterator iter = related.iterator();
			while(iter.hasNext()){
				PointerTargetNode node = (PointerTargetNode) iter.next();
				result.addAll(WordnetUtils.returnWords(node.getSynset()));
			}
		}
		return result;
	}

	public static List<String> returnEntailments(String word, POS pos,
			 SynsetsStrategy strategy, Dictionary wordnet) throws JWNLException {
		List<String> result = new LinkedList<String>();
		List<Synset> senses = findSynsets(word, pos, wordnet, strategy);
		for (Synset sense : senses) {
			PointerTargetNodeList related = PointerUtils.getInstance()
					.getEntailments(sense);
			Iterator iter = related.iterator();
			while (iter.hasNext()) {
				PointerTargetNode node = (PointerTargetNode) iter.next();
				result.addAll(WordnetUtils.returnWords(node.getSynset()));
			}
		}
		return result;
	}
}
