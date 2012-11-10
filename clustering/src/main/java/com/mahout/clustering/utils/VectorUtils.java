package com.mahout.clustering.utils;

import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.mahout.math.SequentialAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.Vector.Element;



public class VectorUtils {

	public static double calculateVecotorLength(Vector vector){
		return Math.sqrt(vector.zSum());
	}
	
	public static double calculateVectorMult(Vector d1, Vector d2){
		Vector multiple = d1.times(d2);
		return multiple.zSum();
	}
	
	public static double mean(Vector v){
		return v.zSum()/v.size();
	}
	
	public static double standardDeviation(Vector v) {
		double mean = mean(v);
		double sum = 0;
		int nonZero = 0;
		Iterator<Element> iter = v.iterateNonZero();
		while(iter.hasNext()){
			Element e = iter.next();
			double tmp = e.get() - mean;
			sum += tmp * tmp;
			nonZero++;
		}		
		sum += mean*mean*(v.size()-nonZero);
		return Math.sqrt(sum / v.size());
	}
	
	public static double covariance(Vector d1, Vector d2){
		double rez = 0.0;
		double mean1 = mean(d1);
		double mean2 = mean(d2);
		
		Iterator<Element> iter1 = d1.iterateNonZero();		
		while(iter1.hasNext()) {
			Element e1 = iter1.next();
			double val1 = e1.get(), val2 = d2.get(e1.index());
			rez += (val1-mean1)*(val2-mean2);
		}
		Iterator<Element> iter2 = d2.iterateNonZero();
		while(iter2.hasNext()) {
			Element e2 = iter2.next();
			double val2 = e2.get();
			if(d1.get(e2.index()) == 0){
				continue;
			}
			rez += (-mean1)*(val2-mean2);
		}
		return rez / d1.size();
	}
	
	public static SequentialAccessSparseVector getDocumentVector(String s, int totalTerms){
		StringTokenizer tokenizer = new StringTokenizer(s);
		int size = Integer.parseInt(tokenizer.nextToken());
		//FIXME: what is cardinality?
		SequentialAccessSparseVector vector = new SequentialAccessSparseVector(totalTerms, totalTerms);
		for (int i = 0; i < size; i++) {
			int index = Integer.parseInt(tokenizer.nextToken());
			double value = Double.parseDouble(tokenizer.nextToken());
			vector.set(index, value);
		}
		return vector;
	}
}
