package com.mahout.clustering.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PointFactory {

	public static Collection<Point> getPoints(int numberPoints, int maxSize){
		Set<Point> points = new HashSet<Point>();
		
		for (int i = 0; i < numberPoints; i++) {
			double x = (double)(new Random().nextInt(maxSize*100))/100;
			double y = (double)(new Random().nextInt(maxSize*100))/100;
			Point p = new Point(x, y);
			points.add(p);
		}
		
		return points;
	}
}
