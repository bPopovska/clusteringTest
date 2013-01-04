package com.mahout.clustering.evaluation.supervised;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.Vector;


public class Entropy extends SupervisedEvaluator{

	
	public Entropy(Map<Integer, List<Vector>> allVectors,
			List<Cluster> allClusters, DistanceMeasure distanceMeasure) {
		super(allVectors, allClusters, distanceMeasure);
		
	}
		
	private double entropy(Cluster c){
		double result = 0;
		Set<Integer> classes = getAllVectors().keySet();
		for (Integer i:classes) {
			double p = calculatePij(i, c);
			result  += p*Math.log(p);
		}
		return result;
	}
	
	@Override
	public double evaluate(){
		double result = 0;
		for(Cluster c:getAllClusters()){
			result += ((double)c.getNumPoints()/getTotal())*entropy(c);
		}
		return result;
	}
	
	
}
