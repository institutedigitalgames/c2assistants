package com.idg.idgweb.iconoscope;

public class Evaluation {
	static public final String[] fitnesses = { 
		"colorDifference",		//0
		"shapeDifference",		//1
		"distanceDifference",	//2
		"groupingDifference",	//3
		"shapeColorDifference"	//4
	};
	
	public static double evaluateGenericDifference(Canvas canvas1, Canvas canvas2, int fitnessID){
		if(fitnessID==0){ return evaluateColorDifference(canvas1,canvas2); }
		if(fitnessID==1){ return evaluateShapeDifference(canvas1,canvas2); }
		if(fitnessID==2){ return evaluateDistances(canvas1,canvas2); }
		if(fitnessID==3){ return evaluateGroupingDifference(canvas1,canvas2); }
		if(fitnessID==4){ return evaluateShapeColorDifference(canvas1,canvas2); }
		//System.out.println("Evaluation: wrong fitness index");
		return 0;
	}
	
	public static double evaluateColorDifference(Canvas canvas1, Canvas canvas2){
		int sameColors = 0;
		int differentColors = 0;
		for(int c=0;c<Shape.colorNames.length;c++){
			String currColor = Shape.colorNames[c];
			int hasColor = 0;
			//System.out.println("Testing for "+currColor);
			for(int i=0;i<canvas1.Shapes.size();i++){
				if(currColor.equalsIgnoreCase(canvas1.Shapes.get(i).Color)){
					//System.out.println("canvas1 has "+canvas1.shapes.get(i).colorName);
					hasColor++;
					break;
				}
			}
			for(int j=0;j<canvas2.Shapes.size();j++){
				if(currColor.equalsIgnoreCase(canvas2.Shapes.get(j).Color)){
					//System.out.println("canvas2 has "+canvas2.shapes.get(j).colorName);
					hasColor++;
					break;
				}
			}
			if(hasColor==1){ differentColors++; }
			if(hasColor==2){ sameColors++; }
		}
		//System.out.println("differentColors = "+differentColors+", sameColors = "+sameColors);
		return (double)(differentColors)/(double)(sameColors+differentColors);
	}
		/*
		for(int i=0;i<canvas1.shapes.size();i++){
			String targetColor = canvas1.shapes.get(i).colorName;
			for(int j=0;j<canvas2.shapes.size();j++){
				if(targetColor.equalsIgnoreCase(canvas2.shapes.get(j).colorName)){
					sameColors++;	// only once, not in the "reverse" version
					break;	
				}
				// this is reached if not broken
				differentColors++;
			}
		}
		// and reverse
		for(int j=0;j<canvas2.shapes.size();j++){
			String targetColor = canvas2.shapes.get(j).colorName;
			for(int i=0;i<canvas1.shapes.size();i++){
				if(targetColor.equalsIgnoreCase(canvas1.shapes.get(i).colorName)){
					break;	
				}
				// this is reached if not broken
				differentColors++;
			}
		}
		return (double)(differentColors)/(double)(sameColors+differentColors);
		*/
	
	public static double evaluateShapeDifference(Canvas canvas1, Canvas canvas2){
		int sameShapes = 0;
		int differentShapes = 0;
		for(int s=0;s<Shape.shapeNames.length;s++){
			String currShape = Shape.shapeNames[s];
			int hasShape = 0;
			for(int i=0;i<canvas1.Shapes.size();i++){
				if(currShape.equalsIgnoreCase(canvas1.Shapes.get(i).ID)){
					hasShape++;
					break;
				}
			}
			for(int j=0;j<canvas2.Shapes.size();j++){
				if(currShape.equalsIgnoreCase(canvas2.Shapes.get(j).ID)){
					hasShape++;
					break;
				}
			}
			if(hasShape==1){ differentShapes++; }
			if(hasShape==2){ sameShapes++; }
		}
		//System.out.println("differentShapes = "+differentShapes+", sameShapes = "+sameShapes);
		return (double)(differentShapes)/(double)(2*sameShapes+differentShapes);
	}
	
	public static double evaluateShapeColorDifference(Canvas canvas1, Canvas canvas2){
		/*
		double sDiff = evaluateShapeDifference(canvas1,canvas2);
		double cDiff = evaluateColorDifference(canvas1,canvas2);
		return Math.sqrt(Math.pow(sDiff,2)+Math.pow(cDiff,2));
		*/
		
		int sameColors = 0;
		int differentColors = 0;
		
		int sameShapes = 0;
		int differentShapes = 0;
		
		int sameColorShapes = 0;
		int differentColorShapes = 0;
		
		int sameDebugColors = 0;
		int differentDebugColors = 0;
		
		for(int c=0;c<Shape.colorNames.length;c++){
			String currColor = Shape.colorNames[c];
			for(int i=0;i<canvas1.Shapes.size();i++){
				if(currColor.equalsIgnoreCase(canvas1.Shapes.get(i).Color)){
					String targetShape = canvas1.Shapes.get(i).ID;
					for(int j=0;j<canvas2.Shapes.size();j++){
						if(currColor.equalsIgnoreCase(canvas2.Shapes.get(j).Color)){
							// established same color
							if(targetShape.equalsIgnoreCase(canvas2.Shapes.get(j).ID)){
								sameColorShapes++;
							}
						}
					}
				}
			}
		}
		double sDiff = evaluateShapeDifference(canvas1,canvas2);
		double cDiff = evaluateColorDifference(canvas1,canvas2);
		
		//System.out.println("sameColors = "+sameColors+"but "+differentColors+", DEBUG sameColors = "+sameDebugColors+"but "+differentDebugColors);
		
		//System.out.println("sameColorShapes = "+sameColorShapes+", ShapeDiff = "+sDiff+", ColorDiff = "+cDiff);
		// max size
		return -10*sameColorShapes+sDiff+cDiff;
	}
	
	public static double evaluateDistances(Canvas canvas1, Canvas canvas2){
		double[] proximity = new double[canvas1.Shapes.size()];
		for(int i=0;i<canvas1.Shapes.size();i++){
			Shape shape1 = canvas1.Shapes.get(i);
			// init
			proximity[i]=0;
			for(int j=0;j<canvas2.Shapes.size();j++){
				Shape shape2 = canvas2.Shapes.get(j);
				double dist = Math.sqrt(Math.pow(shape1.PosX-shape2.PosX,2)+Math.pow(shape1.PosY-shape2.PosY,2));
				proximity[i]+=dist;
			}
			// average
			proximity[i]=proximity[i]/canvas2.Shapes.size();
		}
		double result = StatisticUtils.average(proximity);
		return result;
	}
	
	public static double evaluateGroupingDifference(Canvas canvas1, Canvas canvas2){
		double grouping1 = getCanvasGrouping(canvas1);
		double grouping2 = getCanvasGrouping(canvas2);
		return Math.abs(grouping1-grouping2);
	}
	
	protected static double getCanvasGrouping(Canvas canvas){
		double grouping = 0;
		if(canvas.Shapes.size()<=1){ return 1; }
		for(int i=0;i<canvas.Shapes.size();i++){
			Shape shape1 = canvas.Shapes.get(i);
			for(int j=i+1;j<canvas.Shapes.size();j++){
				Shape shape2 = canvas.Shapes.get(j);
				double dist = Math.sqrt(Math.pow(shape1.PosX-shape2.PosX,2)+Math.pow(shape1.PosY-shape2.PosY,2));
				grouping+=dist;
			}
		}
		return grouping/(canvas.Shapes.size()*(canvas.Shapes.size()-1));
	}
	/*
	static double evaluateOverlap(Canvas canvas1, Canvas canvas2){
		// calculate bounding boxes
		for(int i=0;i<canvas1.shapes.size();i++){
			Shape shape1 = canvas1.shapes.get(i);
			for(int j=0;j<canvas2.shapes.size();j++){
				Shape shape2 = canvas2.shapes.get(j);
				
			}
		}
		return 0;
	}
	*/
}
