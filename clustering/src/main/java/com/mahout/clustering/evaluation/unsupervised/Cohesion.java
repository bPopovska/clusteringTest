package com.mahout.clustering.evaluation.unsupervised;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.mahout.clustering.AbstractCluster;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.Vector;

public class Cohesion<T extends AbstractCluster> extends UnsupervisedEvaluator<T>{
	
	Map<Vector, Double> allPoints = new HashMap<Vector, Double>();			

	public Cohesion(List<T> clusters, List<Vector> allPoints, DistanceMeasure distanceMeasure) {
		super(clusters, distanceMeasure);
		
		for (Vector v:allPoints) {
			double min = getMinDistance(v);
			this.allPoints.put(v, min);
		}
	}


	private double getMinDistance(Vector v) {
		
		double min = Double.MAX_VALUE;
		int index = 1;
		int minIndex = -1;
		
		
		for(AbstractCluster c : clusters){
			double distance = distanceMeasure.distance(c.getCenter(), v);
			if(distance < min){
				min = distance;
				minIndex = index;
			}
			index++;
		}
		System.out.print(v + " ");
		System.out.println(minIndex);
		return min;
	}



	@Override
	public double evaluate(){
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
		
}
