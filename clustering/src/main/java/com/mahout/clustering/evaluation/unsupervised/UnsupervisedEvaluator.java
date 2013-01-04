package com.mahout.clustering.evaluation.unsupervised;

import java.util.List;

import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.common.distance.DistanceMeasure;

import com.mahout.clustering.evaluation.Evaluator;

abstract public class UnsupervisedEvaluator implements Evaluator {
	
	List<Cluster> clusters;
	
	DistanceMeasure distanceMeasure;

	public UnsupervisedEvaluator(List<Cluster> clusters,
			DistanceMeasure distanceMeasure) {
		this.clusters = clusters;
		this.distanceMeasure = distanceMeasure;
	}

	public List<Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}

	public DistanceMeasure getDistanceMeasure() {
		return distanceMeasure;
	}

	public void setDistanceMeasure(DistanceMeasure distanceMeasure) {
		this.distanceMeasure = distanceMeasure;
	}
	
	

}
