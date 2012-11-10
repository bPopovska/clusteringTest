package com.mahout.clustering.filters;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.AttributeSource;

import com.mahout.clustering.stemmer.Steemer;


@SuppressWarnings("all")
public class CustomStemFilter extends TokenFilter{
	
	Steemer stemmer;
	private AttributeSource.State current;
	private final TermAttribute termAtt;
	
	public CustomStemFilter(TokenStream in, Steemer stemmer) {
		super(in);
		this.stemmer = stemmer;
		this.termAtt = addAttribute(TermAttribute.class);
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (!input.incrementToken())
			return false;
		String term = termAtt.term();
		current = captureState();
		termAtt.setTermBuffer(stemmer.findStem(term));
		return true;
	}
}
