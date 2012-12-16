package com.mahout.clustering.evaluation;

import java.util.List;

import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.common.distance.DistanceMeasure;

public class Separation {

	private List<Cluster> clusters;
	
	private DistanceMeasure distanceMeasure;
	
	private int total;

	public Separation(List<Cluster> clusters, DistanceMeasure distanceMeasure, int total) {
		this.clusters = clusters;
		this.distanceMeasure = distanceMeasure;
		this.total = total;
	}
	
	public double calculate(){
		double result = 0;
		for (int i = 0; i < clusters.size(); i++) {
			Cluster clusterI = clusters.get(i);
			for (int j = i+1; j < clusters.size(); j++) {
				Cluster clusterJ = clusters.get(j);
				result += ((double)clusterI.getNumPoints()/total)*distanceMeasure.distance(clusterI.getCenter(), clusterJ.getCenter());
			}
		}
		return result;
	}
	
	
	

}
