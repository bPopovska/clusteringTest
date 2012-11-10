package com.mahout.clustering.demos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class NLPTokenizerDemo {
	public static void main(String[] args) throws IOException {
		NLPTokenizerDemo pos = new NLPTokenizerDemo();
		pos.parse();
	}

	public String[] parse() throws IOException {
		InputStream modelIn = new FileInputStream("models/en-token.bin");
		String text = "Pierre Vinken, 61 years old, will join the board as a nonexecutive director Nov. 29. Mr. Vinken is chairman of Elsevier N.V., the Dutch publishing group. Rudolph Agnew, 55 years old and former chairman of Consolidated Gold Fields PLC, was named a director of this British industrial conglomerate.";
		String tokens[] = null;
		try {
			TokenizerModel tokenModel = new TokenizerModel(modelIn);
			Tokenizer tokenizer = new TokenizerME(tokenModel);
			tokens = tokenizer.tokenize(text);
			for (String token : tokens) {
				System.out.println(token);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (IOException e) {
				}
			}
		}
		return tokens;

	}
}
