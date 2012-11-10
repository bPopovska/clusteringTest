package com.mahout.clustering.model;

import java.util.Collection;

import org.apache.commons.math.stat.clustering.Clusterable;

public class Point implements Clusterable<Point> {
	
	double x;
	
	double y;
	
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double distanceFrom(Point p) {
		return Math.sqrt((x-p.x)*(x-p.x) + (y-p.y)*(y-p.y));
	}

	public Point centroidOf(Collection<Point> p) {
		int size = p.size();
		
		double x = 0.0;
		double y = 0.0;
		
		for(Point point:p){
			x += point.x;
			y += point.y;
		}
		
		Point result = new Point(x/size, y/size);
		return result;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	

}
