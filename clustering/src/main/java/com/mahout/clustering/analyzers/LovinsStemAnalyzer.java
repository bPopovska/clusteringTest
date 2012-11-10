package com.mahout.clustering.analyzers;

import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.ClassicTokenizer;
import org.apache.lucene.util.Version;
import org.tartarus.snowball.ext.LovinsStemmer;

import com.mahout.clustering.filters.SynonymFilter;
import com.mahout.clustering.synonym_engines.SynonymEngine;

public class LovinsStemAnalyzer extends Analyzer {
	Version version;
	Set<String> stopwords;
	SynonymEngine engine;
	
	public LovinsStemAnalyzer(Version version, Set<String> stopwords,
			SynonymEngine engine) {
		super();
		this.version = version;
		this.stopwords = stopwords;
		this.engine = engine;
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		return new SynonymFilter(new SnowballFilter(new StopFilter(version,
				new LowerCaseFilter(version, new ClassicTokenizer(version,
						reader)), stopwords), new LovinsStemmer()), engine);
	}
}
