package com.idg.idgweb.iconoscope.comparators;

import com.idg.idgweb.iconoscope.Canvas;
import com.idg.idgweb.iconoscope.Shape;

import java.util.Comparator;

public class DepthSizeComparator implements Comparator {
	public int compare(Object curr, Object other){
		if(other instanceof Shape && curr instanceof Shape){
			Shape currShape = ((Shape)curr);
			Shape otherShape = ((Shape)other);
			double currDepth = currShape.Depth;
			double otherDepth = otherShape.Depth;
			if(currDepth==otherDepth){
				// sort by size (large size goes last)
				double currScale = Math.min(currShape.ScaleX,currShape.ScaleY);
				double otherScale = Math.min(otherShape.ScaleX,otherShape.ScaleY);
				return Double.compare(currScale, otherScale);
			} else {
				// high depth goes last
				return Double.compare(currDepth, otherDepth);
			}
		} else {
			//System.out.println("DepthSizeComparator: not comparing with another Shape");
			return 0;
		}
	}
};