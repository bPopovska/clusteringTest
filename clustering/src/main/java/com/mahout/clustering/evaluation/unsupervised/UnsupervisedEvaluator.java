package com.mahout.clustering.evaluation.unsupervised;

import java.util.List;

import org.apache.mahout.clustering.AbstractCluster;
import org.apache.mahout.common.distance.DistanceMeasure;

import com.mahout.clustering.evaluation.Evaluator;

abstract public class UnsupervisedEvaluator<T extends AbstractCluster> implements Evaluator {
	
	List<T> clusters;
	
	DistanceMeasure distanceMeasure;

	public UnsupervisedEvaluator(List<T> clusters,
			DistanceMeasure distanceMeasure) {
		this.clusters = clusters;
		this.distanceMeasure = distanceMeasure;
	}

	public List<T> getClusters() {
		return clusters;
	}

	public void setClusters(List<T> clusters) {
		this.clusters = clusters;
	}

	public DistanceMeasure getDistanceMeasure() {
		return distanceMeasure;
	}

	public void setDistanceMeasure(DistanceMeasure distanceMeasure) {
		this.distanceMeasure = distanceMeasure;
	}
	
	

}
