package com.mahout.clustering.filters;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.dictionary.Dictionary;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeSource;

import com.mahout.clustering.model.WordnetProperties;
import com.mahout.clustering.utils.WordnetUtils;
import com.mahout.clustering.wordnet.SynsetsStrategy;

@SuppressWarnings("all")
public class WordnetFilter extends TokenFilter {

	private Dictionary wordnet;

	private SynsetsStrategy conceptStrategy;

	private WordnetProperties wordNetProperties;
	private AttributeSource.State current;
	private Stack<String> stack;
	

	private String currentType = "";
	private final TermAttribute termAtt;
	private final TypeAttribute typeAtt;

	public WordnetFilter(TokenStream in, WordnetProperties wordNetProperties,
			SynsetsStrategy conceptStrategy, Dictionary wordent) {
		super(in);
		this.wordNetProperties = wordNetProperties;
		this.wordnet = wordent;
		this.conceptStrategy = conceptStrategy;
		stack = new Stack<String>();
		termAtt = addAttribute(TermAttribute.class);
		typeAtt = addAttribute(TypeAttribute.class);
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (stack.size() > 0) {
			String word = stack.pop();
			restoreState(current);
			termAtt.setTermBuffer(word);
			typeAtt.setType(currentType);
			return true;
		}
		if (!input.incrementToken()){
			return false;
		}
		if (addToStack()) {
			current = captureState();
		}
		return true;
	}
	
	private boolean addToStack(){
		String type = typeAtt.type();
		String term = termAtt.term();
		currentType = type;
		List<String> results = new LinkedList<String>();
		POS pos = null;
		
		if (type.equals("noun")) {
			pos = POS.NOUN;
		} else if (type.equals("verb")) {
			pos = POS.VERB;
		} else if (type.equals("adverb")) {
			pos = POS.ADVERB;
		} else if (type.equals("adjective")) {
			pos = POS.ADJECTIVE;
		}
		try {
			if(wordNetProperties.isAddHypernyms()){
				results.addAll(WordnetUtils.returnHypernims(term, pos, wordNetProperties.getGeneralizationLevels(), conceptStrategy, wordnet));
			}
			if(wordNetProperties.isAddMeronyms()){
				results.addAll(WordnetUtils.returnMeronyms(term, pos, conceptStrategy, wordnet));
			}
			if(wordNetProperties.isAddConditionality()){
				results.addAll(WordnetUtils.returnEntailments(term, pos, conceptStrategy, wordnet));
			}
		} catch (JWNLException exception) {
			return false;
		}
		if(results.size() == 0){
			return false;
		}
		for (String s:results) {
			stack.push(s);
		}
		return true;
	}

}
