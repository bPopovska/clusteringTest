package com.mahout.clustering.analyzers;

import java.io.Reader;
import java.util.Set;

import net.didion.jwnl.dictionary.Dictionary;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.ClassicTokenizer;
import org.apache.lucene.util.Version;

import com.mahout.clustering.filters.CustomStemFilter;
import com.mahout.clustering.filters.POSFilter;
import com.mahout.clustering.filters.SpecificTermsFilter;
import com.mahout.clustering.filters.SynonymFilter;
import com.mahout.clustering.filters.WordnetFilter;
import com.mahout.clustering.model.WordnetProperties;
import com.mahout.clustering.stemmer.Steemer;
import com.mahout.clustering.synonym_engines.SynonymEngine;
import com.mahout.clustering.wordnet.SynsetsStrategy;

public class CustomStemAnalyzer extends Analyzer {

	Version version;
	Set<String> stopwords;
	SynonymEngine engine;
	Steemer stemmer;
	Dictionary wordnet;
	WordnetProperties properties;
	SynsetsStrategy synsetStrategy;
	

	public CustomStemAnalyzer(Version version, Set<String> stopwords,
			SynonymEngine engine, Steemer stemmer, Dictionary wordnet, WordnetProperties wordnetProperties, SynsetsStrategy synsetStrategy) {
		super();
		this.version = version;
		this.stopwords = stopwords;
		this.engine = engine;
		this.stemmer = stemmer;
		this.wordnet = wordnet;
		this.properties = wordnetProperties;
		this.synsetStrategy = synsetStrategy;
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		
		SpecificTermsFilter specificTermsFilter = new SpecificTermsFilter(new StopFilter(version, new LowerCaseFilter(version, new ClassicTokenizer(
				version, reader)), stopwords));
		POSFilter stemerFilter = new POSFilter(specificTermsFilter);
		return new WordnetFilter(new SynonymFilter(new CustomStemFilter(
				 stemerFilter, stemmer), engine), properties, synsetStrategy, wordnet);
	}
}
