package com.mahout.clustering.demos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.mahout.math.SequentialAccessSparseVector;
import org.apache.mahout.math.Vector;

import com.mahout.clustering.utils.FormatingUtils;
import com.mahout.clustering.utils.VectorUtils;

public class CustomIndexReader {
	
	
	List<Vector> files = new LinkedList<Vector>();
	
	List<String> allterms = new LinkedList<String>();
	
	List<String> filenames = new LinkedList<String>();
	
	public static void main(String[] args) throws IOException{
		new CustomIndexReader().readVectorDocuments();
		System.out.println("done");
	}

	public void readVectorDocuments() throws FileNotFoundException,
			IOException {
		String filename = "vectors_" + FormatingUtils.analyzerParametersToString();
		File terms = new File(filename + "/allterms");
		BufferedReader reader = new BufferedReader(new FileReader(terms));
		int totalNumberOfTerms = 0;
		while(true){
			String line = reader.readLine();
			if(StringUtils.isBlank(line)){
				break;
			}
			totalNumberOfTerms++;
			allterms.add(line.trim());
		}
		
		File vectorIndexFile = new File(filename + "/files");
		reader = new BufferedReader(new FileReader(vectorIndexFile));
		while(true){
			String line = reader.readLine();
			if(StringUtils.isEmpty(line)){
				break;
			}
			SequentialAccessSparseVector documentVector = VectorUtils.getDocumentVector(line.trim(), totalNumberOfTerms);
			files.add(documentVector);
		}
		
		File fileNames = new File(filename + "/filenames");
		reader = new BufferedReader(new FileReader(fileNames));
		while(true){
			String line = reader.readLine();
			if(StringUtils.isEmpty(line)){
				break;
			}
			filenames.add(line.trim());
		}
		
	}

	public List<Vector> getFiles() {
		return files;
	}

	public void setFiles(List<Vector> files) {
		this.files = files;
	}

	public List<String> getAllterms() {
		return allterms;
	}

	public void setAllterms(List<String> allterms) {
		this.allterms = allterms;
	}

	public List<String> getFilenames() {
		return filenames;
	}

	public void setFilenames(List<String> filenames) {
		this.filenames = filenames;
	}
	
	
}
