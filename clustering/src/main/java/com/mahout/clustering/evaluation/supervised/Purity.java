package com.mahout.clustering.evaluation.supervised;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.mahout.clustering.AbstractCluster;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.Vector;


public class Purity<T extends AbstractCluster> extends SupervisedEvaluator<T>{

	public Purity(Map<Integer, List<Vector>> allVectors,
			List<T> allClusters, DistanceMeasure distanceMeasure) {
		super(allVectors, allClusters, distanceMeasure);
	}

	@Override
	public double evaluate() {
		double result = 0;
		int totalNumberOfVectors = getAllVectors().size();
		for (T c : getAllClusters()) {
			result += ((double) c.getNumPoints()/totalNumberOfVectors) * calculateP(c); 
		}
		return result;
	}
	
	private double calculateP(T c){
		double result = 0;
		Set<Integer> allClassIds = getAllVectors().keySet();
		for (Integer j : allClassIds) {
			double pij = calculatePij(j, c);
			if(pij > result){
				result = pij;
			}
		}
		return result;
	}
	
	

	
}
