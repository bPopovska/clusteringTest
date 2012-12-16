package com.mahout.clustering.evaluation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.Vector;

public class Cohesion {
	
	List<Cluster> clusters;
	
	Map<Vector, Double> allPoints = new HashMap<Vector, Double>();
	
	DistanceMeasure distanceMeasure;
	

	public Cohesion(List<Cluster> clusters, List<Vector> allPoints, DistanceMeasure distanceMeasure) {
		this.clusters = clusters;
		for (Vector v:allPoints) {
			double min = getMinDistance(v);
			this.allPoints.put(v, min);
		}
	}



	private double getMinDistance(Vector v) {
		
		double min = Double.MAX_VALUE;
		
		for(Cluster c:clusters){
			double distance = distanceMeasure.distance(c.getCenter(), v);
			if(distance < min){
				min = distance;
			}
		}
		return min;
	}



	public double calculate(){
		double result = 0;
		Set<Vector> keySet = allPoints.keySet();
		for(Vector v:keySet){
			result += allPoints.get(v);
		}
		return result;
	}
	
	public Map<Vector, Double> getAllPoints() {
		return allPoints;
	}
	
	public List<Cluster> getClusters() {
		return clusters;
	}
	
	public DistanceMeasure getDistanceMeasure() {
		return distanceMeasure;
	}
}
