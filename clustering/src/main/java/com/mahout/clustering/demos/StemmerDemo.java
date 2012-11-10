package com.mahout.clustering.demos;

import org.tartarus.snowball.ext.LovinsStemmer;

public class StemmerDemo {
	public static void main(String[] args) {
		LovinsStemmer stemmer = new LovinsStemmer();
		stemmer.setCurrent("going");
		stemmer.stem();
		stemmer.getCurrent();
	}
}
