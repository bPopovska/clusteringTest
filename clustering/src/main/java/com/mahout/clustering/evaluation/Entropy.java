package com.mahout.clustering.evaluation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.Vector;

public class Entropy {

	private Map<Integer, List<Vector>> allVectors = new HashMap<Integer, List<Vector>>();
	
	private List<Cluster> allClusters = new LinkedList<Cluster>();
	
	private long total;
	private DistanceMeasure distanceMeasure;
	
	
	public Entropy(Map<Integer, List<Vector>> allVectors,
			List<Cluster> allClusters, DistanceMeasure distanceMeasure, long total) {
		super();
		this.allVectors = allVectors;
		this.allClusters = allClusters;
		this.distanceMeasure = distanceMeasure;		
		this.total = total;
	}



	private double calculateP(int i, Cluster c){
		double res = 0;
		int count = 0;
		List<Vector> vector = allVectors.get(i);
		double min = Double.MAX_VALUE;
		int clusterId = 0;
		
		for (Vector v:vector) {
			for(Cluster cTmp:allClusters){
				double dist = distanceMeasure.distance(cTmp.getCenter(), v);
				if(dist < min){
					min = dist;
					clusterId = cTmp.getId();
				}
			}
			if(clusterId == c.getId()){
				count++;
			}
		}

		res =(double)count / c.getNumPoints();
		return res;
	}
	
	private double entropy(Cluster c){
		double result = 0;
		Set<Integer> classes = allVectors.keySet();
		for (Integer i:classes) {
			double p = calculateP(i, c);
			result  += p*Math.log(p);
		}
		return result;
	}
	
	public double calculate(){
		double result = 0;
		for(Cluster c:allClusters){
			result += ((double)c.getNumPoints()/total)*entropy(c);
		}
		return result;
	}
	
	
}
