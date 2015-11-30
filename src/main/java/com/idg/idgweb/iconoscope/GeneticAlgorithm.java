package com.idg.idgweb.iconoscope;

import com.idg.idgweb.iconoscope.comparators.CanvasFitnessComparator;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class GeneticAlgorithm {
	static final int neighbors = 5;
	
	Canvas template = null;
	
	ArrayList<Canvas> population;
	ArrayList<Canvas> archive;
	
	public GeneticAlgorithm(Canvas userCanvas, int size, boolean useArchive){
		initPopulation(userCanvas,size);
		//System.out.println("Population initialized without archive");
		if(useArchive){
			archive = new ArrayList<Canvas>();
			archive.add(userCanvas);
			//System.out.println("Archive added");
		}
	}
	public GeneticAlgorithm(Canvas userCanvas, int size){
		initPopulation(userCanvas,size);
		// now what?
		//System.out.println("Population initialized");
		//printPopulation();
	}
	
	public void initPopulation(int size){
		population = new ArrayList<Canvas>();
		// chaotic kate is simple: first just copy the canvas
		for(int i=0;i<size;i++){
			Canvas individual = SaveRandomConcepts.getRandomConcept();
			population.add(individual);
			//System.out.println("add another clone");
		}
	}
	public void initPopulation(Canvas userCanvas, int size){
		population = new ArrayList<Canvas>();
		// chaotic kate is simple: first just copy the canvas
		for(int i=0;i<size;i++){
			Canvas individual = userCanvas.createCopy();
			individual.mutate();
			population.add(individual);
			//System.out.println("add another clone");
		}
	}
	
	public void printPopulation(){
		/*
		for(int p=0;p<population.size();p++){
			System.out.println("Individual "+p);
			for(int s=0;s<population.get(p).Shapes.size();s++){
				System.out.println("shape "+s+": "+population.get(p).Shapes.get(s).Color+" "+population.get(p).Shapes.get(s).ID);
			}
		}
		*/
	} 
	
	public void setTemplate(Canvas template){ this.template = template; } 
	
	public void evolve(int generations, int distanceID, boolean increase){
		for(int g=0;g<generations;g++){
			if(archive!=null){
				evaluateDistPopulationAndArchive(distanceID,increase);
			} else {
				evaluateDist(distanceID,increase);
			}
			sortPopulation();
			
			// add best to the archive (if we're using that)
			if(archive!=null){
				archive.add(population.get(0).createCopy());
			}
			
			double[] nrmFns = getNormalizedFitness(population);
			// make roulette wheel
			WeightedRandomTable<Canvas> roulette = new WeightedRandomTable<Canvas>();
			for(int i=0;i<population.size();i++){
				//System.out.println("nrmFns[i] = "+nrmFns[i]);
				roulette.addEntry((float)Math.max(0.01f,nrmFns[i]), population.get(i));
			}
			
			// debug
			roulette.print();
			// elitism of population/2
			ArrayList<Canvas> nextGen = new ArrayList<Canvas>();
			int elitism = population.size()/2;
			for(int i=0;i<elitism;i++){
				Canvas toAdd = population.get(i).createCopy();
				nextGen.add(toAdd);
			}
			for(int i=elitism;i<population.size();i++){
				Canvas toAdd = roulette.getRandomEntry().createCopy();
				toAdd.mutate();
				//if(i>=elitism){ toAdd.mutate(); }
				nextGen.add(toAdd);
			}
			// replace
			population.clear();
			population = nextGen;
		}
		evaluateDist(distanceID,increase);
		//printPopulation();
	}
	
	public double[] getNormalizedFitness(ArrayList<Canvas> population){
		double[] result = new double[population.size()];
		
		// find borders
		double maxValue = Double.NEGATIVE_INFINITY;
		double minValue = Double.POSITIVE_INFINITY;
	
		for(int i=0;i<result.length;i++){
			maxValue = Math.max(maxValue, population.get(i).Fitness);
			minValue = Math.min(minValue, population.get(i).Fitness);
		}
		
		//if(maxValue==minValue){ System.out.println("Equal max and min fitness at "+maxValue); }

		// normalize
		for(int i=0;i<result.length;i++){
			if(maxValue==minValue){ 
				result[i] = 0.01;
			} else {
				result[i] = (population.get(i).Fitness-minValue)/(maxValue-minValue);
			}
		}
		
		return result;
	}
	
	public void evaluateDist(int distanceID, boolean increase){
		for(int i=0;i<population.size();i++){
			if(template==null){
				// novelty
				double[] distances_to_i = new double[population.size()-1];
				double novelty = 0;
				int count = 0;
				for(int j=0;j<population.size();j++){
					if(i!=j){
						distances_to_i[count] = Evaluation.evaluateGenericDifference(population.get(i), population.get(j), distanceID);
						count++;
					}
				}
				Arrays.sort(distances_to_i);
				for(int j=0;j<distances_to_i.length;j++){
					//System.out.println(distances_to_i[j]);
					if(j<neighbors){ novelty+=distances_to_i[j]; }
				}
				novelty = novelty/(neighbors);
				population.get(i).Fitness = increase ? novelty : -novelty;
				//System.out.println("individual "+i+" novelty = "+population.get(i).Fitness);
			} else {
				// typicality
				double typicality = Evaluation.evaluateGenericDifference(population.get(i), template, distanceID);
				population.get(i).Fitness = increase ? typicality : -typicality;
				//System.out.println("individual "+i+" typicality = "+population.get(i).Fitness);
			}
		}
	}
	public void evaluateDistPopulationAndArchive(int distanceID, boolean increase){
		for(int i=0;i<population.size();i++){
			if(template==null){
				// novelty
				double[] distances_to_i = new double[archive.size()+population.size()-1];
				double novelty = 0;
				int count = 0;
				for(int j=0;j<population.size();j++){
					if(i!=j){
						distances_to_i[count] = Evaluation.evaluateGenericDifference(population.get(i), population.get(j), distanceID);
						count++;
					}
				}
				for(int j=0;j<archive.size();j++){
					distances_to_i[count] = Evaluation.evaluateGenericDifference(population.get(i), archive.get(j), distanceID);					
					count++;
				}
				Arrays.sort(distances_to_i);
				for(int j=0;j<distances_to_i.length;j++){
					System.out.println(distances_to_i[j]);
					if(j<neighbors){ novelty+=distances_to_i[j]; }
				}
				novelty = distances_to_i[0]*novelty/(neighbors);	// IMPORTANT ADDITION: multiply with least fit individual (no duplicates!)
				//novelty = novelty/(neighbors); 
				population.get(i).Fitness = increase ? novelty : -novelty;
				//System.out.println("individual "+i+" novelty = "+population.get(i).Fitness);
			} else {
				// typicality
				double typicality = Evaluation.evaluateGenericDifference(population.get(i), template, distanceID);
				population.get(i).Fitness = increase ? typicality : -typicality;
				//System.out.println("individual "+i+" typicality = "+population.get(i).Fitness);
			}
		}
	}
	public void evaluateDistArchive(int distanceID, boolean increase){
		for(int i=0;i<population.size();i++){
			if(template==null){
				// novelty
				double novelty = 0;
				for(int j=0;j<archive.size();j++){
					novelty += Evaluation.evaluateGenericDifference(population.get(i), archive.get(j), distanceID);
				}
				novelty = novelty/archive.size();
				population.get(i).Fitness = increase ? novelty : -novelty;
				//System.out.println("individual "+i+" novelty = "+population.get(i).Fitness);
			} else {
				// typicality
				double typicality = Evaluation.evaluateGenericDifference(population.get(i), template, distanceID);
				population.get(i).Fitness = increase ? typicality : -typicality;
				//System.out.println("individual "+i+" typicality = "+population.get(i).Fitness);
			}
		}
	}
	
	public void sortPopulation(){
		Collections.sort(population,new CanvasFitnessComparator());
	}
	public void sortPopulation(ArrayList<Canvas> population){
		Collections.sort(population,new CanvasFitnessComparator());
	}
	
	public ArrayList<Canvas> getBest(int number){
		sortPopulation();
		ArrayList<Canvas> result = new ArrayList<Canvas>();
		for(int i=0;result.size()<number && i<population.size();i++){
			if(!exists(population.get(i),result)){
				result.add(population.get(i));
			}
		}
		
		// problem with fewer results? make some random ones!
		while(result.size()<number){
			Canvas addition = result.get(0).createCopy();
			while(addition.isIdentical(result.get(0))){ addition.mutate(); }
			result.add(addition);
		}
		
		return result;
	}
	
	public boolean exists(Canvas target, ArrayList<Canvas> base){
		for(int i=0;i<base.size();i++){
			if(target.isIdentical(base.get(i))){ 
				//System.out.println("Target already exists!");
				return true; 
			}
		}
		return false;
	}
}
