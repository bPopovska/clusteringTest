package com.mahout.clustering;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.clustering.kmeans.KMeansClusterer;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.Vector;

import com.mahout.clustering.demos.CustomIndexReader;


/**
 * Performs simple KMeans Clustering of documents.
 *
 */
public class KMeansDemo 
{
    public static void main( String[] args )
    {
    	
    	CustomIndexReader indexReader = new CustomIndexReader();
    	try {
			indexReader.readVectorDocuments();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	//to be initialized using Canopy
    	int K = 100;
    	
    	double CONVERGENCE_TRASHHOLD = 0.01;
    	
    	DistanceMeasure DISTANCE_MEASURE = new CosineDistanceMeasure();
    	
    	List<Vector> documents = indexReader.getFiles();
    	List<Cluster> clusters = new LinkedList<Cluster>();
    	
    	int clusterId = 1;
    	for (Vector v : documents) {
			clusters.add(new Cluster(v, clusterId++, DISTANCE_MEASURE));
		}
    	
    	List<List<Cluster>> clusterPoints = KMeansClusterer.clusterPoints(documents, clusters, DISTANCE_MEASURE, K, CONVERGENCE_TRASHHOLD);
    	
    	
    	//get the final cluster configuration
    	clusters = clusterPoints.get(clusterPoints.size() - 1);
    	
    	
    }
}
