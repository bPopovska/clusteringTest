package com.mahout.clustering.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Utils {
	
	public static Set<String> fileToSet(File f) throws IOException{
		Set<String> result = new HashSet<String>();
		BufferedReader reader = new BufferedReader(new FileReader(f));
		while(true){
			String line = reader.readLine();
			if(line == null){
				break;
			}
			StringTokenizer tokenizer = new StringTokenizer(line);			
			while(tokenizer.hasMoreTokens()){
				String word = tokenizer.nextToken();
				word = word.toLowerCase();
				result.add(word);
			}
		}
		return result;
	}
	
	public static boolean isNuber(String str){
		return str.matches("^[0-9]*$");
	}
	
}
