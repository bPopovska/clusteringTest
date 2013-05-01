package com.mahout.clustering.evaluation.unsupervised;

import java.util.List;

import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.common.distance.DistanceMeasure;

import com.mahout.clustering.evaluation.unsupervised.UnsupervisedEvaluator;

public class Separation extends UnsupervisedEvaluator{

	private int total;

	public Separation(List<Cluster> clusters, DistanceMeasure distanceMeasure, int total) {
		super(clusters, distanceMeasure);
		this.total = total;
	}
	
	
	@Override
	public double evaluate(){
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
//java!