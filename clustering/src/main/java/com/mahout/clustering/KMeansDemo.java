package com.mahout.clustering;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.mahout.clustering.canopy.Canopy;
import org.apache.mahout.clustering.canopy.CanopyClusterer;
import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.clustering.kmeans.KMeansClusterer;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.Vector;

import com.mahout.clustering.demos.CustomIndexReader;
import com.mahout.clustering.evaluation.unsupervised.Cohesion;
import com.mahout.clustering.evaluation.unsupervised.Separation;


/**
 * Performs simple KMeans Clustering of documents.
 *
 */
public class KMeansDemo 
{
    public static void main( String[] args )
    {
    	
    	CustomIndexReader customIndexReader = new CustomIndexReader();
    	try {
			customIndexReader.readVectorDocuments();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	double T1 = 1;
		double T2 = 1;
				
		double CONVERGENCE_TRASHHOLD = 0.001;
		
		DistanceMeasure DISTANCE_MEASURE = new EuclideanDistanceMeasure();
		
		List<String> allterms = customIndexReader.getAllterms();
		List<String> filenames = customIndexReader.getFilenames();
		List<Vector> files = new LinkedList<Vector>();
		
		// make sure to make a deep copy here
		for (int i = 0; i < customIndexReader.getFiles().size(); i++) {
			files.add(customIndexReader.getFiles().get(i));
		}		
		
		List<Canopy> createCanopies = CanopyClusterer.createCanopies(files, new CosineDistanceMeasure(), T1, T2);
		
    	//to be initialized using Canopy
    	int K = createCanopies.size();
    	
    	// the list is destroyed at this point by the call to createCanopies
    	files = customIndexReader.getFiles();
    	
    	List<Cluster> clusters = new LinkedList<Cluster>();
    	
    	int clusterId = 1;
    	for (Vector v : files) {
			clusters.add(new Cluster(v, clusterId++, DISTANCE_MEASURE));
		}
    	
    	List<List<Cluster>> clusterPoints = KMeansClusterer.clusterPoints(files, clusters, DISTANCE_MEASURE, 10, 0.01);
    	
    	    	
    	// get the final cluster configuration
    	// TODO: see what the value is here?
    	clusters = clusterPoints.get(clusterPoints.size() - 1);

    	Cohesion cohesionMeasure = new Cohesion(clusters, files, DISTANCE_MEASURE);
    	
    	double d = cohesionMeasure.evaluate();
    	
    	System.out.println("Cohesion measure is: " + d);
    	
    	
    	
    }
}
