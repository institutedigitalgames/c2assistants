/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.iconoscope;

import com.idg.idgweb.iconoscope.comparators.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.ParameterCollection;

/**
 *
 * @author c2learn
 */
@XmlRootElement(name = "canvas")
@XmlAccessorType(XmlAccessType.FIELD)
public class Canvas {
	public final static int MAXWIDTH = 1200;
	public final static int MAXHEIGHT = 900;
    
	@XmlElement(name = "shapes")
    public ArrayList<Shape> Shapes = null;
	@XmlElement(name = "fitness")
	public double Fitness;
	@XmlElement(name = "ID")
	public String ID;
	
    public Canvas(){
        Shapes = new ArrayList<Shape>();
		Fitness = 0;
		ID = "";
    }
    
    public Canvas(ArrayList<Shape> shapes){
        this.Shapes = shapes;
		Fitness = 0;
		ID = "";
    }
	
	public void initializeDefault(){
		this.Shapes.clear();
        this.addShape(new Shape("Heart","0xCE0000",0,1,1,0.5*MAXWIDTH,0.5*MAXHEIGHT,1));
		this.addShape(new Shape("Star","0xF7F700",90,1,1,0.5*MAXWIDTH,0.5*MAXHEIGHT,2));
		this.ID = "";
		//result.fitness=-0.1;
	}
	public static Canvas makeDefault(){
        Canvas result = new Canvas();
		result.addShape(new Shape("Heart","0xCE0000",0,1,1,0.5*MAXWIDTH,0.5*MAXHEIGHT,1));
		//result.fitness=-0.1;
		return result;
	}
		/*
			String shapeName, String colorName, double rotation, double scale_x, 
            double scale_y, double scale_z, double canvas_x, double canvas_y, int ID,
            String ID_Name, int ID_Number*/
    
    public void addShape(Shape shape){
        this.Shapes.add(shape);
    }
	
	public Canvas createCopy(){
		Canvas result = new Canvas();
		for(int i=0;i<this.Shapes.size();i++){
			result.addShape(this.Shapes.get(i).createCopy());
		}
		result.ID = this.ID;
		result.Fitness = this.Fitness;
		return result;
	}
	
	public static Canvas fromJSON(JSONObject obj){
		//JSONObject canvas_obj = obj.getJSONObject("canvas");
		Canvas result = new Canvas();
		//result.fitness = canvas_obj.getDouble("fitness");
		result.ID = obj.getString("ID");
		JSONArray json_shapeList = obj.getJSONArray("Shapes");
		//System.out.println("I have "+json_shapeList.length()+" shapes");
		for(int i=0;i<json_shapeList.length();i++){
			result.addShape(Shape.fromJSON(json_shapeList.getJSONObject(i)));
		}
		return result;
	}
	
	public void sanitizeDepth(){
		// here's the simplest version
		
		// here's the version using a Special Comparator
		Collections.sort(Shapes,new DepthSizeComparator());
		for(int i=0;i<Shapes.size();i++){ Shapes.get(i).Depth=i; }
	}
	
	static double mutateAddShapeChance = -0.1;
	static double mutateCloneShapeChance = 0.1;
	static double mutateRemoveShapeChance = 0.1;
	static double mutateChangeShapeAnyChance = 0.1;
	static double mutateChangeColorAnyChance = 0.1;
	static double mutateTranslateAnyChance = 0.02;
	static double mutateRotateAnyChance = 0.1;
	static double mutateScaleAnyChance = 0.1;
    
	public void updateParams(){
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		if(pc.contains("mutateAddShapeChance")){ mutateAddShapeChance = pc.getDouble("mutateAddShapeChance"); }
		if(pc.contains("mutateCloneShapeChance")){ mutateCloneShapeChance = pc.getDouble("mutateCloneShapeChance"); }
		if(pc.contains("mutateRemoveShapeChance")){ mutateRemoveShapeChance = pc.getDouble("mutateRemoveShapeChance"); }
		if(pc.contains("mutateChangeShapeAnyChance")){ mutateChangeShapeAnyChance = pc.getDouble("mutateChangeShapeAnyChance"); }
		if(pc.contains("mutateChangeColorAnyChance")){ mutateChangeColorAnyChance = pc.getDouble("mutateChangeColorAnyChance"); }
		if(pc.contains("mutateTranslateAnyChance")){ mutateTranslateAnyChance = pc.getDouble("mutateTranslateAnyChance"); }
		if(pc.contains("mutateRotateAnyChance")){ mutateRotateAnyChance = pc.getDouble("mutateRotateAnyChance"); }
		if(pc.contains("mutateScaleAnyChance")){ mutateScaleAnyChance = pc.getDouble("mutateScaleAnyChance"); }
	}
	
	public void mutate(){
		if(Shapes==null ||Shapes.isEmpty()){ return; }	// sanity check
		updateParams();
		//Random r = new Random();
		double roll;
		ThreadLocalRandom rand = java.util.concurrent.ThreadLocalRandom.current();
		roll = rand.nextDouble();
		//System.out.println("roll = "+roll);
		if(roll<mutateAddShapeChance){
			//System.out.println("mutateAddShape");
			mutateAddShape();
		}
		roll = rand.nextDouble();
		//System.out.println("roll = "+roll);
		if(roll<mutateCloneShapeChance){
			//System.out.println("mutateCloneShape");
			mutateCloneShape();
		}
		roll = rand.nextDouble();
		//System.out.println("roll = "+roll);
		if(Shapes.size()>=2 && roll<mutateRemoveShapeChance){
			//System.out.println("mutateRemoveShape");
			mutateRemoveShape();
		}
		
		for(int i=0;i<Shapes.size();i++){
			if(rand.nextDouble()<mutateChangeShapeAnyChance){
				//System.out.println("mutateShape");
				Shapes.get(i).mutateShape();
			} else if(rand.nextDouble()<mutateChangeColorAnyChance){
				//System.out.println("mutateColor");
				Shapes.get(i).mutateColor();
			} else if(rand.nextDouble()<mutateTranslateAnyChance){
				//System.out.println("mutateTranslation");
				Shapes.get(i).mutateTranslation();
			} else if(rand.nextDouble()<mutateRotateAnyChance){
				//System.out.println("mutateRotation");
				Shapes.get(i).mutateRotation();
			} else if(rand.nextDouble()<mutateScaleAnyChance){
				//System.out.println("mutateScale");
				Shapes.get(i).mutateScale();
			}
		}
		
		this.sanitizeDepth();
		//System.out.println("mutation done!");
	}
	public void mutateAddShape(){
		Shape randomAddition = new Shape("","",0,0,0,0,0,0);
		randomAddition.randomize();
		Shapes.add(randomAddition);
	}
	public void mutateCloneShape(){
		ThreadLocalRandom rand = java.util.concurrent.ThreadLocalRandom.current();
		int roll = rand.nextInt(Shapes.size());
		Shape cloned = Shapes.get(roll);
		Shape clone = cloned.createCopy();
		// make (only) one change
		int changeRoll = rand.nextInt(5);
		if(changeRoll==0){ 
			clone.mutateShape();
		} else if(changeRoll==1){ 
			clone.mutateColor();
		} else if(changeRoll==2){ 
			clone.mutateTranslation();
		} else if(changeRoll==3){ 
			clone.mutateRotation();
		} else if(changeRoll==4){ 
			clone.mutateScale();
		}
		Shapes.add(clone);
	}
	public void mutateRemoveShape(){
		ThreadLocalRandom rand = java.util.concurrent.ThreadLocalRandom.current();
		int roll = rand.nextInt(Shapes.size());
		Shapes.remove(roll);
	}
	
	public boolean isIdentical(Canvas other){
		if(Shapes.size()!=other.Shapes.size()){ return false; }
		// ordering also (apparently) important
		for(int i=0;i<Shapes.size();i++){
			Shape targetShape = Shapes.get(i);
			if(!targetShape.ID.equalsIgnoreCase(other.Shapes.get(i).ID)){ return false; }
			if(!targetShape.Color.equalsIgnoreCase(other.Shapes.get(i).Color)){ return false; }
			if(Math.abs(targetShape.PosX-other.Shapes.get(i).PosX)+Math.abs(targetShape.PosY-other.Shapes.get(i).PosY)<10){ return false; }
			if(Math.abs(targetShape.ScaleX-other.Shapes.get(i).ScaleX)+Math.abs(targetShape.ScaleY-other.Shapes.get(i).ScaleY)<0.5){ return false; }
			if(targetShape.Rotation!=other.Shapes.get(i).Rotation){ return false; }
		}
		return true;
	}
}
