package com.mahout.clustering.evaluation.supervised;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.Vector;


public class Purity extends SupervisedEvaluator{

	public Purity(Map<Integer, List<Vector>> allVectors,
			List<Cluster> allClusters, DistanceMeasure distanceMeasure) {
		super(allVectors, allClusters, distanceMeasure);
	}

	@Override
	public double evaluate() {
		double result = 0;
		int totalNumberOfVectors = getAllVectors().size();
		for (Cluster c : getAllClusters()) {
			result += ((double) c.getNumPoints()/totalNumberOfVectors) * calculateP(c); 
		}
		return result;
	}
	
	private double calculateP(Cluster c){
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
