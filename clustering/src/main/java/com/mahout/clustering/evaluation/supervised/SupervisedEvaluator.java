package com.mahout.clustering.evaluation.supervised;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.mahout.clustering.AbstractCluster;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.Vector;

import com.mahout.clustering.evaluation.Evaluator;

abstract class SupervisedEvaluator<T extends AbstractCluster> implements Evaluator{
	
	/**
	 * Hash map that keeps track of all available vectors and the class they belong to.
	 */
	private Map<Integer, List<Vector>> allVectors = new HashMap<Integer, List<Vector>>();
	
	private List<T> allClusters = new LinkedList<T>();
	
	private DistanceMeasure distanceMeasure;
		
	
	public SupervisedEvaluator(Map<Integer, List<Vector>> allVectors,
			List<T> allClusters, DistanceMeasure distanceMeasure) {
		this.allVectors = allVectors;
		this.allClusters = allClusters;
		this.distanceMeasure = distanceMeasure;
	
	}
	
	protected long calculateMij(int j, T c) {
		long count = 0;
		List<Vector> vector = allVectors.get(j);
		double min = Double.MAX_VALUE;
		int clusterId = 0;
		
		for (Vector v:vector) {
			for(T cTmp:allClusters){
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
		return count;
	}

	/**
	 * pij = mij / mi
	 * 
	 * @param j The number of the class.
	 * @param c The cluster.
	 * @return The p parameter.
	 */
	protected double calculatePij(int j, T c){
		return (double) calculateMij(j, c) / c.getNumPoints();
		
	}
	
	protected long getTotal(){
		long result = 0;
		Set<Integer> allClassIds = allVectors.keySet();
		for (Integer j : allClassIds) {
			result += allVectors.get(j).size();
		
		}
		return result;
	}

	public Map<Integer, List<Vector>> getAllVectors() {
		return allVectors;
	}

	public void setAllVectors(Map<Integer, List<Vector>> allVectors) {
		this.allVectors = allVectors;
	}

	public List<T> getAllClusters() {
		return allClusters;
	}

	public void setAllClusters(List<T> allClusters) {
		this.allClusters = allClusters;
	}

	public DistanceMeasure getDistanceMeasure() {
		return distanceMeasure;
	}

	public void setDistanceMeasure(DistanceMeasure distanceMeasure) {
		this.distanceMeasure = distanceMeasure;
	}
	
	

}
