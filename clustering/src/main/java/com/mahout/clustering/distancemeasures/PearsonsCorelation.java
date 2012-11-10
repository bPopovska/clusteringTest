package com.mahout.clustering.distancemeasures;

import java.util.Collection;

import org.apache.hadoop.conf.Configuration;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.parameters.Parameter;
import org.apache.mahout.math.Vector;

import com.mahout.clustering.utils.VectorUtils;


public class PearsonsCorelation implements DistanceMeasure {
	
	
	public double distance(Vector v1, Vector v2) {
		return 0;
	}

	public void configure(Configuration arg0) {
		
		
	}

	public void createParameters(String arg0, Configuration arg1) {
		// TODO Auto-generated method stub
		
	}

	public Collection<Parameter<?>> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public double distance(double centroidLengthSquare, Vector vec1, Vector vec2) {
		double std1 = VectorUtils.standardDeviation(vec1);
		double std2 = VectorUtils.standardDeviation(vec2);
		double corelation = VectorUtils.covariance(vec1, vec2);
		double tmp = corelation/(std1*std2);
		if(tmp >= 0){
			tmp = 1 - tmp;
		}
		else{
			tmp = - tmp;
		}
		return tmp;
	}
}
