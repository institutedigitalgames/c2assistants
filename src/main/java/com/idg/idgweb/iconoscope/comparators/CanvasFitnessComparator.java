package com.idg.idgweb.iconoscope.comparators;

import com.idg.idgweb.iconoscope.Canvas;
import java.util.Comparator;

public class CanvasFitnessComparator implements Comparator {
	public int compare(Object curr, Object other){
		if(other instanceof Canvas && curr instanceof Canvas){
			double currFitness = ((Canvas)curr).Fitness;
			double otherFitness = ((Canvas)other).Fitness;
			return -Double.compare(currFitness, otherFitness);
		} else {
			//System.out.println("CanvasFitnessComparator: not comparing with another Canvas");
			return 0;
		}
	}
};
