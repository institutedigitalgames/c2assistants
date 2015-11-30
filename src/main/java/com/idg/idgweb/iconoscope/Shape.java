/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.iconoscope;

import static com.idg.idgweb.iconoscope.Canvas.mutateAddShapeChance;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONObject;
import org.json.JSONArray;
import utils.ParameterCollection;

/**
 *
 * @author c2learn
 */
@XmlRootElement(name = "shape")
@XmlAccessorType(XmlAccessType.FIELD)
public class Shape {
	final static public String[] colorNames = {
		"0x77B500",	"0x007218", "0x00BA9B", "0x0456AF", "0x7C00FF", "0xCE0000", "0xF27E00", "0xF7F700", "0xFFFFFF", "0x000000" 
		//"green",	"darkgreen","darkcyan",	"blue",		"purple",	"darkred",	"orange",	"yellow",	"white",	"black"
	};
	final static public String[] shapeNames = {
		"Circle", "Diamond", "HalfCircle", "Heart", "Hexagon", "RoundRect", "Square", "Star", "Triangle1", "Triangle2"
	};
		
    public String ID;
    public String Color;
    public double Rotation;
    public double ScaleX;
    public double ScaleY;
    public int Depth;
    public double PosX;
    public double PosY;
    
    public Shape() {
        
    }
    
    // Temporary Attributes for the shape list
    public Shape(String ID, String color, double rotation, double scale_x, 
            double scale_y, double canvas_x, double canvas_y, int depth){
        
        this.ID = ID;
        this.Color = color;
        this.Rotation = rotation;
        this.ScaleX = scale_x;
        this.ScaleY = scale_y;
        this.PosX = canvas_x;
        this.PosY = canvas_y;
        this.Depth = depth;
		sanitizeTranslation();
        sanitizeRotation();
		sanitizeScale();
    }
	
	public Shape createCopy(){
		Shape clone = new Shape(this.ID, this.Color, this.Rotation, this.ScaleX, this.ScaleY, this.PosX, this.PosY, this.Depth);
		return clone;
	}
	//{"Rotation":0, "BoundingBoxGlobal":"x:789.55,y:385,w:104,h:90", "Depth":0, "PosY":430, "Color":"0x7c00ff", "PosX":841.55, "ID":"Hexagon", "ScaleY":1.3333282470703125, "ScaleX":1.3333282470703125, "BoundingBoxLocal":"x:-39,y:-33.75,w:78,h:67.5" },
	
	public static Shape fromJSON(JSONObject obj){
		String ID = obj.getString("ID");
		String Color = obj.getString("Color");
		double Rotation = obj.getDouble("Rotation"); 
		double ScaleX = obj.getDouble("ScaleX"); 
        double ScaleY = obj.getDouble("ScaleY");  
		double PosX = obj.getDouble("PosX");   
		double PosY = obj.getDouble("PosY");  
		int Depth = obj.getInt("Depth");
		
		return new Shape(ID,Color,Rotation,ScaleX,ScaleY,PosX,PosY,Depth);
	}
	
	protected void sanitizeRotation(){
		this.Rotation = this.Rotation%360;
		if(this.Rotation<0){
			Rotation=360-this.Rotation;
		}
	}
	protected void sanitizeTranslation(){
		int drawLimits_Xmin = Canvas.MAXWIDTH/4;
		int drawLimits_Xmax = 3*Canvas.MAXWIDTH/4;
		int drawLimits_Ymin = Canvas.MAXHEIGHT/4;
		int drawLimits_Ymax = 3*Canvas.MAXHEIGHT/4;
		if(this.PosX<drawLimits_Xmin){ this.PosX=drawLimits_Xmin; }
		if(this.PosX>drawLimits_Xmax){ this.PosX=drawLimits_Xmax; }
		if(this.PosY<drawLimits_Ymin){ this.PosY=drawLimits_Ymin; }
		if(this.PosY>drawLimits_Ymax){ this.PosY=drawLimits_Ymax; }
		
		// if we want to limit to within canvas
		/*
		this.canvas_x = Math.max(0, canvas_x);
		this.canvas_x = Math.min(Canvas.MAXWIDTH, canvas_x);
			
		this.canvas_y = Math.max(0, canvas_y);
		this.canvas_y = Math.min(Canvas.MAXHEIGHT, canvas_y);
		*/
	}
	protected void sanitizeScale(){
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		double mutateMinScale = 0.5;
		if(pc.contains("mutateMinScale")){ mutateMinScale = pc.getDouble("mutateMinScale"); }
		this.ScaleX = Math.max(mutateMinScale, ScaleX);
		this.ScaleY = Math.max(mutateMinScale, ScaleY);
	}
	public void randomize(){
		//ThreadLocalRandom r = new ThreadLocalRandom();
		ThreadLocalRandom r = java.util.concurrent.ThreadLocalRandom.current();
		this.ID = shapeNames[r.nextInt(shapeNames.length)];
		this.Color = colorNames[r.nextInt(colorNames.length)];
		
		this.PosX = r.nextDouble();
		this.PosY = r.nextDouble();
		this.Rotation = r.nextDouble();
		
		double globalScale = 1+0.5*r.nextGaussian();
		this.ScaleX = globalScale;
		this.ScaleY = globalScale;
		this.Depth = 0;
	}
	public void mutateShape(){
		ThreadLocalRandom r = java.util.concurrent.ThreadLocalRandom.current();
		String potentialName;
		int sanityCount = 10;
		do {
			potentialName = shapeNames[r.nextInt(shapeNames.length)];
			sanityCount--;
		} while(sanityCount>0 && potentialName.equalsIgnoreCase(this.ID));
		this.ID = potentialName;
	}
	public void mutateColor(){
		ThreadLocalRandom r = java.util.concurrent.ThreadLocalRandom.current();
		String potentialName;
		int sanityCount = 10;
		do {
			potentialName = colorNames[r.nextInt(colorNames.length)];
			sanityCount--;
		} while(sanityCount>0 && potentialName.equalsIgnoreCase(this.Color));
		this.Color = potentialName;
	}
	public void mutateTranslation(){
		ThreadLocalRandom r = java.util.concurrent.ThreadLocalRandom.current();
		
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		double mutateTranslateMod = 0.1;
		if(pc.contains("mutateTranslateMod")){ mutateTranslateMod = pc.getDouble("mutateTranslateMod"); }
		
		double incX = mutateTranslateMod*r.nextInt(Canvas.MAXWIDTH);
		double incY = mutateTranslateMod*r.nextInt(Canvas.MAXHEIGHT);
		if(r.nextBoolean()){ this.PosX += incX; } else { this.PosX -= incX; }
		if(r.nextBoolean()){ this.PosY += incY; } else { this.PosY -= incY; }
		
		//this.PosX += r.nextGaussian()*0.1*Canvas.MAXWIDTH;
		//this.PosY += r.nextGaussian()*0.1*Canvas.MAXHEIGHT;
		//
		this.PosX = (int)this.PosX;
		this.PosY = (int)this.PosY;
		sanitizeTranslation();
	}
	public void mutateRotation(){
		ThreadLocalRandom r = java.util.concurrent.ThreadLocalRandom.current();
		this.Rotation += r.nextInt(4)*45;
		sanitizeRotation();		// ensure within 0-360
	}
	public void mutateScale(){
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		double mutateScaleMod = 0.25;
		if(pc.contains("mutateScaleMod")){ mutateScaleMod = pc.getDouble("mutateScaleMod"); }
		
		double globalScale = 1+mutateScaleMod*RandomNumberManager.getRandomGaussian();
		
		double mutateLimScale = 0.75;
		if(pc.contains("mutateMinScale")){ mutateLimScale = 1+pc.getDouble("mutateMinScale")/2; }
		
		if(ScaleX<mutateLimScale || ScaleY<mutateLimScale){ globalScale = Math.abs(globalScale); }
		this.ScaleX = globalScale*ScaleX;
		this.ScaleY = globalScale*ScaleY;
		sanitizeScale();
	}
	
	public void isOverlapping(Shape otherShape){
		
	}
}
