package com.idg.idgweb.scribes.custom;

import com.idg.idgweb.scribes.ElementCard;
import com.idg.idgweb.scribes.Elements;
import java.io.File;
import java.util.ArrayList;
import wox.serial.Easy;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.ParameterCollection;

public class CustomElementList {
	private ArrayList<ElementCard> elementList = null;//createElementList();
    private ArrayList<ElementCard> mythList = null;//createMythList();
    
    public CustomElementList(String deckID){
        initElementsLists(deckID);
    }
	static public void saveCustomElementList(ArrayList<ElementCard> elementList, String deckID){
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		//System.out.println("Saving new Element List XML!");
		Easy.save(elementList, pc.getString("4scribes_decks") + File.separator + deckID + "_elementList.xml");
	}
	static public void saveCustomMythList(ArrayList<ElementCard> mythList, String deckID){
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		//System.out.println("Saving new Element List XML!");
		Easy.save(mythList, pc.getString("4scribes_decks") + File.separator + deckID + "_mythList.xml");
	}
	
    public void initElementsLists(String deckID) {
        // Create the Elements List
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		//System.out.println("Element List Path ---> " + pc.getString("4scribes_decks") + File.separator + deckID + "_elementList.xml");
        File f = new File(pc.getString("4scribes_decks") + File.separator + deckID + "_elementList.xml");
        if (f.exists()) {
            elementList = (ArrayList<ElementCard>) Easy.load(pc.getString("4scribes_decks") + File.separator + deckID + "_elementList.xml");
        } else {
			elementList = null;
		}
        
        // Create the Myth List
        //System.out.println("Myth Element List Path ---> " + pc.getString("4scribes_decks") + File.separator + deckID + "_mythList.xml");
        f = new File(pc.getString("4scribes_decks") + File.separator + deckID + "_mythList.xml");
        if (f.exists()) {
            mythList = (ArrayList<ElementCard>) Easy.load(pc.getString("4scribes_decks") + File.separator + deckID + "_mythList.xml");
        } else {
			mythList = null;
		}
    }
    
    public void saveElementsLists(String deckID) {
        ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		Easy.save(elementList, pc.getString("4scribes_decks") + File.separator + deckID + "_elementList.xml");
		Easy.save(mythList, pc.getString("4scribes_decks") + File.separator + deckID + "_mythList.xml");
    }
    public ArrayList<ElementCard> getElementList() { return elementList; }
    public ArrayList<ElementCard> getMythList() { return mythList; }
    
    public ElementCard getElementByID(int elementID){
        //if(elementList == null || mythList == null){ initElementsLists(); }
        for(ElementCard c: elementList){
            if(c.getID() == elementID){
                return c;
            }
        }
        
        for(ElementCard c: mythList){
            if(c.getID() == elementID){
                return c;
            }
        }
        
        return null;
    }
	public ElementCard getElementByName(String name){
        //if(elementList == null || mythList == null){ initElementsLists(); }
        for(ElementCard c: elementList){
            if(c.getName().equalsIgnoreCase(name)){
                return c;
            }
        }
        
        for(ElementCard c: mythList){
            if(c.getName().equalsIgnoreCase(name)){
                return c;
            }
        }
        
        return null;
    }
	
    public void incrementTimePlayed(int elementID){
        //if(elementList == null){ elementList = createElementList(); }
        //if(mythList == null){ mythList = createMythList(); }
        
        for(ElementCard c: elementList){
            if(c.getID() == elementID){
                c.incPlayed();
            }
        }
        
        for(ElementCard c: mythList){
            if(c.getID() == elementID){
                c.incPlayed();
            }
        }
	}
}
