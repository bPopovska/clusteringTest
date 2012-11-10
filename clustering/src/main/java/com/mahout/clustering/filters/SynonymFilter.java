package com.mahout.clustering.filters;

import java.io.IOException;
import java.util.Stack;

import net.didion.jwnl.data.POS;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeSource;

import com.mahout.clustering.synonym_engines.SynonymEngine;
@SuppressWarnings("all")
public class SynonymFilter extends TokenFilter {
	public static final String TOKEN_TYPE_SYNONYM = "SYNONYM";
	private Stack<String> synonymStack;
	private SynonymEngine engine;
	private AttributeSource.State current;
	private final TermAttribute termAtt;
	private final PositionIncrementAttribute posIncrAtt;
	private final TypeAttribute typeAtt;

	public SynonymFilter(TokenStream in, SynonymEngine engine) {
		super(in);
		synonymStack = new Stack<String>();
		this.engine = engine;
		this.termAtt = addAttribute(TermAttribute.class);
		this.typeAtt = addAttribute(TypeAttribute.class);
		this.posIncrAtt = addAttribute(PositionIncrementAttribute.class);
	}

	public boolean incrementToken() throws IOException {
		if (synonymStack.size() > 0) {
			String syn = synonymStack.pop();
			restoreState(current);
			termAtt.setTermBuffer(syn);
			posIncrAtt.setPositionIncrement(0);
			return true;
		}
		if (!input.incrementToken())
			return false;
		if (addAliasesToStack()) {
			current = captureState();
		}
		return true;
	}

	private boolean addAliasesToStack() throws IOException {
		POS pos = null;
		String type = typeAtt.type();
		if(type.equals("noun")){
			pos = POS.NOUN;
		} else if(type.equals("verb")){
			pos = POS.VERB;
		} else if(type.equals("adjective")){
			pos = POS.ADJECTIVE;
		} else if(type.equals("adverb")){
			pos = POS.ADVERB;
		}
		String[] synonyms = engine.getSynonyms(termAtt.term(), pos);
		if (synonyms == null) {
			return false;
		}
		for (String synonym : synonyms) {
			synonymStack.push(synonym);
		}
		return true;
	}
}