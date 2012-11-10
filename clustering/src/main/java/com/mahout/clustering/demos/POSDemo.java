package com.mahout.clustering.demos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class POSDemo {

	public static void main(String[] args) throws IOException {
		NLPTokenizerDemo tokenizer = new NLPTokenizerDemo();
		POSDemo pos = new POSDemo();
		String tags[] = pos.tag(tokenizer.parse());
		for(String tag:tags){
			System.out.println(tag);
		}
	}

	public String[] tag(String tokens[]) {
		InputStream modelIn = null;
		String tags[] = null;
		try {
			modelIn = new FileInputStream("models/en-pos-maxent.bin");
			POSModel model = new POSModel(modelIn);
			POSTaggerME tagger = new POSTaggerME(model);
			tags = tagger.tag(tokens);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tags;
	}
}
