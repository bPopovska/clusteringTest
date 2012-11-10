package com.mahout.clustering;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.apache.commons.math.stat.clustering.Cluster;
import org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer;

import com.mahout.clustering.model.Point;
import com.mahout.clustering.model.PointFactory;

/**
 * Hello world!
 *
 */
public class KMeansDemo 
{
    public static void main( String[] args )
    {
    	
    	int K = 100;
    	
    	int maxIterations = 100;
    	
    	Collection<Point> points = PointFactory.getPoints(1000, 1000);
    	
    	KMeansPlusPlusClusterer<Point> kMeansPlusPlusClusterer = new KMeansPlusPlusClusterer<Point>(new Random());
    	List<Cluster<Point>> cluster = kMeansPlusPlusClusterer.cluster(points, K, maxIterations);
    	
    	int index = 0;
    	for(Cluster<Point> c:cluster){
    		List<Point> pointsInCluster = c.getPoints();
    		Point center = (Point) c.getCenter();
    		
    		System.out.println("Points in cluster number " + index);
    		System.out.println("Cluster X = " + center.getX() + " Y = " + center.getY());
    		
    		for(Point pointInC:pointsInCluster){
    			System.out.println("X = " + pointInC.getX() + "Y = " + pointInC.getY());
    		}
    	
    		index++;
    		System.out.println("==========================================================================");
    	}
    	
    }
}
