package com.mahout.clustering;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.mahout.clustering.canopy.Canopy;
import org.apache.mahout.clustering.canopy.CanopyClusterer;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.Vector;

import com.mahout.clustering.demos.CustomIndexReader;
import com.mahout.clustering.evaluation.unsupervised.Cohesion;
import com.mahout.clustering.results.ResultWritter;
import com.mahout.clustering.utils.FormatingUtils;

public class CanopyDemo {
	
	public static void main(String[] args) throws IOException{
		
		//FIXME: how to initialize these values?
		double T1 = 0.001;
		double T2 = 0.00001;
		
		ResultWritter resultWritter = new ResultWritter();
				
		DistanceMeasure distanceMeasure = new CosineDistanceMeasure();
		
		CustomIndexReader customIndexReader = new CustomIndexReader();
		customIndexReader.readVectorDocuments();
		//List<String> allterms = customIndexReader.getAllterms();
		//List<String> filenames = customIndexReader.getFilenames();
		List<Vector> files = customIndexReader.getFiles();
		
		List<Vector> filesCopy = new LinkedList<Vector>();
		
		for (int i = 0; i < files.size(); i++) {
			filesCopy.add(files.get(i));
		}
		
		List<Canopy> allCanopies = CanopyClusterer.createCanopies(filesCopy, distanceMeasure, T1, T2);
		
		Cohesion<Canopy> cohesion = new Cohesion<Canopy>(allCanopies, files, distanceMeasure);
		
		double cohesionValue = cohesion.evaluate();
		
		resultWritter.setOverride(false);
		Map<String, BigDecimal> resultsToWrite = new HashMap<String, BigDecimal>();
		
		resultsToWrite.put("CANOPY_Cohesion_" + FormatingUtils.analyzerParametersToString(), new BigDecimal(cohesionValue));
		resultWritter.setResults(resultsToWrite );
		
		resultWritter.writeResults();
		
		
	}

}
