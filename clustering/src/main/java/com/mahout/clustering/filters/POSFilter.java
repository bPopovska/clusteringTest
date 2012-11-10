package com.mahout.clustering.filters;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeSource;

@SuppressWarnings("all")
public class POSFilter extends TokenFilter {

	private AttributeSource.State current;
	private final TermAttribute termAtt;
	private final TypeAttribute typeAtt;
	private POSTaggerME tagger = null;

	public POSFilter(TokenStream stream) {
		super(stream);
		this.termAtt = addAttribute(TermAttribute.class);
		this.typeAtt = addAttribute(TypeAttribute.class);
		InputStream modelIn = null;
		try {
			modelIn = new FileInputStream("models/en-pos-maxent.bin");
			POSModel model = new POSModel(modelIn);
			tagger = new POSTaggerME(model);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (!input.incrementToken())
			return false;
		String term = termAtt.term();
		String originalTag = typeAtt.type();
		current = captureState();
		String tag = tagger.tag(term);
		String basicTag = "";
		if(originalTag.equals("posIgnore")){
			basicTag = originalTag;
		}
		else{
			basicTag = getBasicPOS(tag);
		}
		termAtt.setTermBuffer(term);
		typeAtt.setType(basicTag);
		return true;
	}

	private String getBasicPOS(String pos) {
		String rez = "";
		if (pos.contains("/")) {
			pos = pos.substring(pos.indexOf("/")+1);
		}
		if (pos.startsWith("V")) {
			rez = "verb";
		} else if (pos.startsWith("N")) {
			rez = "noun";
		} else if (pos.startsWith("J")) {
			rez = "adjective";
		} else if (pos.startsWith("R")){
			rez = "adverb";
		} else {
			rez = "other";
		}
		return rez;
	}
}
