package com.mahout.clustering.demos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.dictionary.Dictionary;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.ClassicAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

import com.mahout.clustering.analyzers.CustomStemAnalyzer;
import com.mahout.clustering.analyzers.PoterStemAnalyzer;
import com.mahout.clustering.model.InputParams;
import com.mahout.clustering.stemmer.WordnetStemmer;
import com.mahout.clustering.synonym_engines.WordNetSynonymEngine;
import com.mahout.clustering.utils.AnalyzerUtils;
import com.mahout.clustering.utils.Utils;



public class Main {

	public static void main(String[] args) throws IOException, JWNLException {
		
		File stopwords = new File("C:\\Users\\bpopovsk\\DiplomskaFiles\\stop_words_long.txt");
		Set<String> setStopWords = Utils.fileToSet(stopwords);
		Version version = Version.LUCENE_34;
		
		Dictionary wordnet = null;
		JWNL.initialize(new FileInputStream("JWNLproperties.xml"));
		wordnet = Dictionary.getInstance();
		
		WordnetStemmer stemmer = new WordnetStemmer();
		WordNetSynonymEngine engine = new WordNetSynonymEngine();
		
		engine.setNumberSynsets(1);
		
		Analyzer analyzers[] = new Analyzer[] {
			new StandardAnalyzer(version, stopwords),
			new ClassicAnalyzer(version, stopwords),
			new PoterStemAnalyzer(version, setStopWords, engine),
			new CustomStemAnalyzer(version, setStopWords, engine, stemmer, wordnet, null, InputParams.SYNSET_STRATEGY)};
		
		File input = new File("C:\\Users\\bpopovsk\\DiplomskaFiles\\small_set");
		File output = new File("C:\\Users\\bpopovsk\\DiplomskaFiles\\small_set_analysis");	
		if(!output.exists()){
			output.mkdir();
		}
		File[] files = input.listFiles();
		BufferedReader reader = null;
		for (File f : files) {
			PrintWriter out = new PrintWriter(output.getPath() + "\\" + f.getName());		
			for (Analyzer analyzer : analyzers) {
				reader = new BufferedReader(new FileReader(f));
				out.println("Analyzer: " + analyzer.getClass().getName());
				while (true) {
					String str = reader.readLine();
					if (str == null) {
						break;
					}
					AnalyzerUtils.displayTokensWithFullDetails(analyzer, str, out);
				}
				out.println();
			}
			out.close();
		}
	}
}
