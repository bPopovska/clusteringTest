package com.mahout.clustering;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.mahout.clustering.results.ResultWritter;
import com.mahout.clustering.utils.FormatingUtils;


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
    	
    	double T1 = 0.001;
		double T2 = 0.00001;
					
		double CONVERGENCE_TRASHHOLD = 0.001;
		
		DistanceMeasure DISTANCE_MEASURE = new EuclideanDistanceMeasure();
		
		//List<String> allterms = customIndexReader.getAllterms();
		//List<String> filenames = customIndexReader.getFilenames();
		List<Vector> files = new LinkedList<Vector>();
		
		// make sure to make a deep copy here
		for (int i = 0; i < customIndexReader.getFiles().size(); i++) {
			files.add(customIndexReader.getFiles().get(i));
		}		
		
		List<Canopy> allCanopies = CanopyClusterer.createCanopies(files, new CosineDistanceMeasure(), T1, T2);
		
    	// maximum number of iterations
    	int maxIterations = allCanopies.size();
    	
    	// the list is destroyed at this point by the call to createCanopies
    	files = customIndexReader.getFiles();
    	
    	List<Cluster> clusters = new LinkedList<Cluster>();
    	
    	int clusterId = 1;
    	
    	// create an initial cluster for each found canopy 
    	for(Canopy  canopy : allCanopies){
    		clusters.add(new Cluster(canopy.getCenter(), clusterId++, DISTANCE_MEASURE));
    	}
    	
    	
    	List<List<Cluster>> clusterPoints = KMeansClusterer.clusterPoints(files, clusters, DISTANCE_MEASURE, maxIterations, CONVERGENCE_TRASHHOLD);
    	
    	clusters = clusterPoints.get(clusterPoints.size() - 1);

    	Cohesion<Cluster> cohesionMeasure = new Cohesion<Cluster>(clusters, files, DISTANCE_MEASURE);
    	
    	double d = cohesionMeasure.evaluate();
    	
    	ResultWritter resultWritter = new ResultWritter();
    	
    	Map<String, BigDecimal> results = new HashMap<String, BigDecimal>();
    	results.put("KMEANS_WITH_CANOPY_Cohesion_" + FormatingUtils.analyzerParametersToString(), new BigDecimal(d));
    	resultWritter.setResults(results);
    	
    	try {
			resultWritter.writeResults();
		} catch (IOException e) {
			System.out.println("An error has occured: " + e.toString());
		}
    	
    	System.out.println("Cohesion measure is: " + d);
    	
    }
}
