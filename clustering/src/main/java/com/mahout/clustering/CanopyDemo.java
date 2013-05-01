package com.mahout.clustering;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math.stat.clustering.Clusterable;
import org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer;
import org.apache.mahout.clustering.canopy.Canopy;
import org.apache.mahout.clustering.canopy.CanopyClusterer;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.math.Vector;

import com.mahout.clustering.demos.CustomIndexReader;

public class CanopyDemo {
	
	public static void main(String[] args) throws IOException{
		
		//FIXME: how to initialize these values?
		double T1 = 0;
		double T2 = 0;
				
		CustomIndexReader customIndexReader = new CustomIndexReader();
		customIndexReader.readVectorDocuments();
		List<String> allterms = customIndexReader.getAllterms();
		List<String> filenames = customIndexReader.getFilenames();
		List<Vector> files = customIndexReader.getFiles();
		
		
		List<Canopy> createCanopies = CanopyClusterer.createCanopies(files, new CosineDistanceMeasure(), T1, T2);
		
		
		
	}

}
