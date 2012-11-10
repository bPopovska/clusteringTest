package com.mahout.clustering.filters;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

public class TaggerFilter extends TokenFilter {
	
	public TaggerFilter(TokenStream stream){
		super(stream);
	}
	
	@Override
	public boolean incrementToken() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
}
