package com.idg.idgweb.iconoscope;

import java.util.Vector;

public class WeightedRandomTable <T> {
	public class WeightedRandomEntry <T> {
		float weight;
		T entry;
		public WeightedRandomEntry(float weight, T entry){
			this.weight = weight;
			this.entry = entry;
		}
		
		public T getEntry(){ return entry; }
		public float getWeight(){ return weight; }
		
		public void setEntry(T entry){ this.entry = entry; }
		public void setWeight(float weight){ this.weight = weight; }
	}
	
	Vector<WeightedRandomEntry<T>> rollingTable;
	float maxRoll;
	
	public WeightedRandomTable(){
		clearTable();
	}
	public void clearTable(){
		rollingTable = new Vector<WeightedRandomEntry<T>>();
		maxRoll = 0;
	}
	public boolean addEntry(float weight, T entry){
		if(weight<=0 || Float.isNaN(weight) || Float.isInfinite(weight)){ 
			//System.out.println("WeightedRandomTable: negative or 0 weight");
			return false; 
		}
		rollingTable.add(new WeightedRandomEntry(weight, entry));
		maxRoll+=weight;
		return true;
	}
	public T getRandomEntry(){
		if(rollingTable==null || rollingTable.isEmpty()){
			System.out.println("WeightedRandomTable: empty table");
			return null;
		}
		float roll = RandomNumberManager.getRandomFloat(0,maxRoll);
		//System.out.println("roll = "+roll+" of "+maxRoll);
		float currMaxWeight=0;
		for(int i=0;i<rollingTable.size();i++){
			currMaxWeight+=rollingTable.get(i).getWeight();
			if(roll<currMaxWeight){
				//System.out.println("chosen = "+i);
				return rollingTable.get(i).getEntry();
			}
		}
		//System.out.println("WeightedRandomTable: entry not found");
		return null;
	}
	public void print(){
		/*
		System.out.println("WeightedRandomTable: "+rollingTable.size()+" entries. (maxRoll = "+maxRoll+")");
		for(int i=0;i<rollingTable.size();i++){
			System.out.println("Entry "+i+": "+rollingTable.get(i).getEntry().toString()+" ("+rollingTable.get(i).getWeight()+") ");
		}
		*/
	}
}
