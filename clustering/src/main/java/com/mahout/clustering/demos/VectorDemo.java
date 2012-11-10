package com.mahout.clustering.demos;

import org.apache.mahout.math.SequentialAccessSparseVector;
import org.apache.mahout.math.Vector;

import com.mahout.clustering.utils.VectorUtils;

public class VectorDemo {

	public static void main(String[] args) {
		Vector vec1 = new SequentialAccessSparseVector(6);
		vec1.set(0, 0.1);
		vec1.set(1, 0.2);
		vec1.set(3, 0.4);
		
		Vector vec2 = new SequentialAccessSparseVector(6);
		vec2.set(1, 0.3);
		vec2.set(2, 0.1);
		vec2.set(3, 0.1);
		vec2.set(4, 0.7);
		
		VectorUtils.calculateVecotorLength(vec1);
		VectorUtils.calculateVectorMult(vec1, vec2);
		VectorUtils.covariance(vec1, vec2);
		VectorUtils.mean(vec1);
		VectorUtils.standardDeviation(vec1);
	//	ClusterDumper dumper = new ClusterDumper(new Parh(), new Path());
	
	}
}
