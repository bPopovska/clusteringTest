package com.mahout.clustering.demos;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.clustering.dirichlet.UncommonDistributions;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.VectorWritable;

public class DirichletDemo {

	private static void generateSamples(List<VectorWritable> vectors, int num,
			double mx, double my, double sd) {
		for (int i = 0; i < num; i++) {
			vectors.add(new VectorWritable(new DenseVector(new double[] {
					UncommonDistributions.rNorm(mx, sd),
					UncommonDistributions.rNorm(my, sd) })));
		}
	}

	public static void main(String[] args) {
		List<VectorWritable> sampleData = new ArrayList<VectorWritable>();
		generateSamples(sampleData, 400, 1, 1, 3);
		generateSamples(sampleData, 300, 1, 0, 0.5);
		generateSamples(sampleData, 300, 0, 2, 0.1);

	/*	DirichletClusterer clusterer = new DirichletClusterer(sampleData,
				new GaussianClusterDistribution(new VectorWritable(
						new DenseVector(2))), 1.0, 10, 2, 2);
		List<Cluster[]> rez = clusterer.cluster(20);*/
	}
}
