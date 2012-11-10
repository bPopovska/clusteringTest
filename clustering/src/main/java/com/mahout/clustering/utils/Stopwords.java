package com.mahout.clustering.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Stopwords {

	public static Set<String> load(String fileName) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
		
		Set<String> result = new HashSet<String>();
		while(true){
			String line = reader.readLine();
			if(line == null){
				break;
			}
			result.add(line.trim().toLowerCase());
		}
		return result;
	}
}
