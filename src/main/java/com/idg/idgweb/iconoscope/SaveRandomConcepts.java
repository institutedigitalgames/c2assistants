package com.idg.idgweb.iconoscope;

import java.io.File;
import utils.ParameterCollection;

import wox.serial.Easy;

public class SaveRandomConcepts {
	static public Canvas getRandomConcept(){
		Canvas result = new Canvas();
		int rollShapes = RandomNumberManager.getRandomInt(1, 5);
		for(int i=0;i<rollShapes;i++){
			result.mutateAddShape();
		}
		// and 10 mutations for good measure
		for(int i=0;i<10;i++){
			result.mutate();
		}
		return result;
	}
	static public void saveRandomConcept(String concept){
		Canvas toSave = getRandomConcept();
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		Easy.save(toSave,pc.getString("iconoscope_typical") + File.separator + concept+".xml");
	}
	
	public static void main(String[] args) {
		String[] conceptsToSave = {
			"love","friendship","war"
		};
		for(int i=0;i<conceptsToSave.length;i++){
			saveRandomConcept(conceptsToSave[i]);
		}
	}
}
