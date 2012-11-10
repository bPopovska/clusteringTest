package com.mahout.clustering.demos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import net.didion.jwnl.dictionary.Dictionary;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import com.mahout.clustering.analyzers.CustomStemAnalyzer;
import com.mahout.clustering.model.InputParams;
import com.mahout.clustering.model.WordnetProperties;
import com.mahout.clustering.stemmer.Steemer;
import com.mahout.clustering.stemmer.WordnetStemmer;
import com.mahout.clustering.synonym_engines.SynonymEngine;
import com.mahout.clustering.synonym_engines.WordNetSynonymEngine;
import com.mahout.clustering.utils.FormatingUtils;
import com.mahout.clustering.utils.Stopwords;

public class CustomIndexWritter {

	public static void main(String[] args) throws IOException{
		new CustomIndexWritter().createDocumentVectors();
	}

	private void createDocumentVectors() throws IOException,
			FileNotFoundException, CorruptIndexException,
			LockObtainFailedException {
		// enum that determines the Lucene library version
		Version version = Version.LUCENE_36;
		
		// a set of stop words used by analyzer
		Set<String> stopWords = Stopwords.load(InputParams.STOP_WORDS_FILE);
		
		// stemmer used in preprocessing
		Steemer stemmer = new WordnetStemmer();
		
		// wordnet dictionary used
		Dictionary wordnet = Dictionary.getInstance();
		
		//wordnet properties
		WordnetProperties wordNetProperties = new WordnetProperties();
		
		wordNetProperties.setAddConditionality(InputParams.CONDITIONALITY);
		wordNetProperties.setAddHypernyms(InputParams.HYPERNYMS);
		wordNetProperties.setAddMeronyms(InputParams.MERONYMS);
		wordNetProperties.setGeneralizationLevels(InputParams.GENERALIZATION_LEVELS);
		
		SynonymEngine engine = new WordNetSynonymEngine();
		
		engine.setNumberSynsets(InputParams.MAX_SYNONYMES);
		
		
		CustomStemAnalyzer customSteamAnalyzer = new CustomStemAnalyzer(version, stopWords, engine, stemmer, wordnet, wordNetProperties);
		//StandardAnalyzer customSteamAnalyzer = new StandardAnalyzer(version);
		
		IndexWriterConfig config = new IndexWriterConfig(version, customSteamAnalyzer);
		File outputIndexFile = new File("output");
		
		Directory dir = new SimpleFSDirectory(outputIndexFile);
		
		//only create the index if it doesn't already exist
		if(!outputIndexFile.exists()){
			outputIndexFile.mkdir();
			File fileNamesIndex = new File("output/filenames");
			PrintWriter pw = new PrintWriter(fileNamesIndex);
			IndexWriter writter = new IndexWriter(dir, config);
			File inputFolder = new File("input");
			for(File file:inputFolder.listFiles()){
				pw.println(file.getName());
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				StringBuffer buffer = new StringBuffer();
				while(true){
					String line = bufferedReader.readLine();
					if(line == null){
						break;
					}
					buffer.append(line + " ");
				}
				Document doc = new Document();
				//FIXME: should it be included
				//doc.add(new Field("title", file.getName(), Field.Store.YES, Field.Index.NO, Field.TermVector.NO));
				Field contentField = new Field("content", buffer.toString(), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES);
				
				doc.add(contentField);
				writter.addDocument(doc);
			}
			writter.close();
			pw.close();
		}

		
		// create tf-idf from the lucene created index
		IndexReader reader = IndexReader.open(dir);
		
		Queue<String> termSet = new PriorityQueue<String>();
		Map<String, Integer> documentFrequencies = new HashMap<String, Integer>();
		TermEnum allTerms = reader.terms();
		
		String vectorDirName = "vectors_" + FormatingUtils.analyzerParametersToString();
		
		File rootVectorFolder = new File(vectorDirName);
		if(!rootVectorFolder.exists()){
			rootVectorFolder.mkdir();
		}
		File fileNameIndexSrc = new File("output/filenames");
		File fileNameIndexDest = new File(vectorDirName + "/filenames");
		FileUtils.copyFile(fileNameIndexSrc, fileNameIndexDest);
		
		File termFiles = new File(vectorDirName + "/allterms");
		
		PrintWriter writter = new PrintWriter(termFiles);
		while(allTerms.next()){
			Term term = allTerms.term();
			String termText = term.text();
			int termFreq = reader.docFreq(term);
			termSet.add(termText);
			writter.println(termText);
			documentFrequencies.put(termText, termFreq);
		}
		writter.close();
		
		File vectorFiles = new File(vectorDirName + "/files");
		writter = new PrintWriter(vectorFiles);
		int numberDocuments = reader.numDocs();
		for (int i = 0; i < numberDocuments; i++) {
			
			TermFreqVector termFreqVector = reader.getTermFreqVector(i, "content");
			if(termFreqVector == null){
				continue;
			}
			int[] termFrequencies = termFreqVector.getTermFrequencies();
			int freqSum = 0;
			for (int j = 0; j < termFrequencies.length; j++) {
				freqSum += termFrequencies[j];
			}
			String[] terms = termFreqVector.getTerms();
			int index = 0;
			int j = 0;
			writter.print(terms.length + " ");
			for(String currentTerm:termSet){
				if(index < terms.length && currentTerm.equals(terms[index])){
					double tf_idf = ((double)termFrequencies[index]/freqSum)*Math.log((double)numberDocuments/documentFrequencies.get(terms[index]));
					writter.print(j + " " + tf_idf + " ");
					index++;
				} 
				j++;
			}
			writter.print("\n");
		}
		writter.close();
	}
}
