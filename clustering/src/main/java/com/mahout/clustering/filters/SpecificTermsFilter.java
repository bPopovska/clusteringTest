package com.mahout.clustering.filters;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeSource;

import com.mahout.clustering.utils.Utils;

@SuppressWarnings("all")
public class SpecificTermsFilter extends TokenFilter {
	
	private AttributeSource.State current;
	private final TermAttribute termAtt; 
	private final TypeAttribute typeAtt;
	
	public SpecificTermsFilter(TokenStream stream){
		super(stream);
		this.termAtt = addAttribute(TermAttribute.class);
		this.typeAtt = addAttribute(TypeAttribute.class);
	}
	
	@Override
	public boolean incrementToken() throws IOException {
		if (!input.incrementToken())
			return false;
		String term = termAtt.term();
		String tag = typeAtt.type();
		
		//check if the string is specific
		
		//1. number
		
		if(Utils.isNuber(term)){
			tag = "posIgnore";
			term = "number";
		}
		
		//2. email address
		else if(tag.equals("<EMAIL>")){
			tag = "posIgnore";
			term = "some_email";
		}
		
		//3. some other special tag
		else if(tag.equals("<HOST>")){
			tag = "posIgnore";
			term = "host";
		}
		
		//TODO: additional special cases
	
		
		termAtt.setTermBuffer(term);
		typeAtt.setType(tag);
		current = captureState();
		return true;
	}
}
