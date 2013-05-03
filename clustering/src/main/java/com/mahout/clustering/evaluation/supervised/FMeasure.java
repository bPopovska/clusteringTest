package com.mahout.clustering.evaluation.supervised;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.mahout.clustering.AbstractCluster;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.Vector;


public class FMeasure<T extends AbstractCluster> extends SupervisedEvaluator<T>{
	
	public FMeasure(Map<Integer, List<Vector>> allVectors,
			List<T> allClusters, DistanceMeasure distanceMeasure) {
		super(allVectors, allClusters, distanceMeasure);
	}

	private double precision(int j, T c){
		return calculatePij(j, c);
	}
	
	private double recall(int j, T c){
		return calculateMij(j, c)/c.getNumPoints();
	}
	
	private double calculateFij(int j, T c){
		double recall = recall(j, c);
		double precision = precision(j, c);
		return 2 * recall * precision / (recall + precision);
	}
	
	private double calculateFj(int j){
		double max = 0;
		for (T c : getAllClusters()) {
			double F = calculateFij(j, c);
			if(F > max){
				max = F;
			}
		}
		return max;
	}


	@Override
	public double evaluate() {
		double result = 0;
		Set<Integer> allClassIds = getAllVectors().keySet();
		for (Integer j : allClassIds) {
			result += (getAllVectors().get(j).size()/getTotal()) * calculateFj(j);
		}
		return result;
	}

}
