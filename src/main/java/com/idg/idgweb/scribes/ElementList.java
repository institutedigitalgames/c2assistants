/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes;

import com.idg.idgweb.scribes.datamanagement.SemanticDiffKey;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import utils.ParameterCollection;
import wox.serial.Easy;

/**
 *
 * @author c2learn
 */
public class ElementList {
    private static ElementList instance = null;
    
    private ArrayList<ElementCard> elementList = null;//createElementList();
    private ArrayList<ElementCard> mythList = null;//createMythList();
    
    private ElementList(){
        initElementsLists();
    }
    
    public static void initInstance(){
        if(instance == null){
            instance = new ElementList();
        }
    }
    
    public static ElementList getInstance(){
        if(instance == null){
            initInstance();
        }
        return instance;
    }
    
    public static void destroyInstance(){
        instance.saveElementsLists();
        instance = null;
    }
    
    public void initElementsLists() {
        // Create the Elements List
        ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		
		//System.out.println(" Element List Path ---> " + pc.getString("4scribes_decks") + File.separator + "elementList.xml");
        File f = new File(pc.getString("4scribes_decks") + File.separator + "elementList.xml");
        if (f.exists()) {
            elementList = (ArrayList<ElementCard>) Easy.load(pc.getString("4scribes_decks") + File.separator + "elementList.xml");
        } else {
            //System.out.println("Creating new Element List XML!");
            elementList = createElementList();
            Easy.save(elementList, pc.getString("4scribes_decks") + File.separator + "elementList.xml");
        }
        
        // Create the Myth List
        //System.out.println("Myth Element List Path ---> " + pc.getString("4scribes_decks") + File.separator + "mythElementList.xml");
        f = new File(pc.getString("4scribes_decks") + File.separator + "mythElementList.xml");
        if (f.exists()) {
            mythList = (ArrayList<ElementCard>) Easy.load(pc.getString("4scribes_decks") + File.separator + "mythElementList.xml");
        } else {
            //System.out.println("Creating new Element List XML!");
            mythList = createMythList();
            Easy.save(mythList, pc.getString("4scribes_decks") + File.separator + "mythElementList.xml");
        }
    }
    
    public void saveElementsLists() {
        ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		Easy.save(elementList, pc.getString("4scribes_decks") + File.separator + "elementList.xml");
        Easy.save(mythList, pc.getString("4scribes_decks") + File.separator + "mythElementList.xml");
    }
    
    public ArrayList<ElementCard> getElementList() {
        return elementList;
        
    }
    
    public ArrayList<ElementCard> getMythList() {
        return mythList;
    }
    
    public ElementCard getElementByID(int elementID){
        if(elementList == null || mythList == null){
            initElementsLists();
        }
        
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
        if(elementList == null || mythList == null){
            initElementsLists();
        }
        
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
        if(elementList == null){
            elementList = createElementList();
        }
        
        if(mythList == null)
            mythList = createMythList();
        
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
    
    private ArrayList<ElementCard> createElementList(){
        elementList = new ArrayList<>();

        // Create Fire Elements
        elementList.add(ElementCard.createElementCard("Agony", Elements.getElement(0), 1));
        elementList.add(ElementCard.createElementCard("Anger", Elements.getElement(0), 2));
        elementList.add(ElementCard.createElementCard("Confusion", Elements.getElement(0), 3));
        elementList.add(ElementCard.createElementCard("Shame", Elements.getElement(0), 4));
        elementList.add(ElementCard.createElementCard("Sadness", Elements.getElement(0), 5));
        elementList.add(ElementCard.createElementCard("Trust", Elements.getElement(0), 6));
        elementList.add(ElementCard.createElementCard("Obsession", Elements.getElement(0), 7));
        elementList.add(ElementCard.createElementCard("Happiness", Elements.getElement(0), 8));
        elementList.add(ElementCard.createElementCard("Pride", Elements.getElement(0), 9));
        elementList.add(ElementCard.createElementCard("Fear", Elements.getElement(0), 10));
        elementList.add(ElementCard.createElementCard("Certainty", Elements.getElement(0), 11));
        elementList.add(ElementCard.createElementCard("Eureka!", Elements.getElement(0), 12));
        
        // Create Fire Element Characters
        elementList.add(ElementCard.createElementCharacter("Baby of Fire", Elements.getElement(0), 13));
        elementList.add(ElementCard.createElementCharacter("Boy of Fire", Elements.getElement(0), 14));
        elementList.add(ElementCard.createElementCharacter("Girl of Fire", Elements.getElement(0), 15));
        elementList.add(ElementCard.createElementCharacter("Man of Fire", Elements.getElement(0), 16));
        elementList.add(ElementCard.createElementCharacter("Woman of Fire", Elements.getElement(0), 17));
        elementList.add(ElementCard.createElementCharacter("Elder Man of Fire", Elements.getElement(0), 18));
        elementList.add(ElementCard.createElementCharacter("Elder Woman of Fire", Elements.getElement(0), 19));
        
        // Create Water Elements
        elementList.add(ElementCard.createElementCard("Learn", Elements.getElement(1), 1));
        elementList.add(ElementCard.createElementCard("Craft", Elements.getElement(1), 2));
        elementList.add(ElementCard.createElementCard("Flee", Elements.getElement(1), 3));
        elementList.add(ElementCard.createElementCard("Deal", Elements.getElement(1), 4));
        elementList.add(ElementCard.createElementCard("Defend", Elements.getElement(1), 5));
        elementList.add(ElementCard.createElementCard("Influence", Elements.getElement(1), 6));
        elementList.add(ElementCard.createElementCard("Scheme", Elements.getElement(1), 7));
        elementList.add(ElementCard.createElementCard("Fight", Elements.getElement(1), 8));
        elementList.add(ElementCard.createElementCard("Travel", Elements.getElement(1), 9));
        elementList.add(ElementCard.createElementCard("Help", Elements.getElement(1), 10));
        elementList.add(ElementCard.createElementCard("Give", Elements.getElement(1), 11));
        elementList.add(ElementCard.createElementCard("Take", Elements.getElement(1), 12));
        
        // Create Water Element Characters
        elementList.add(ElementCard.createElementCharacter("Baby of Water", Elements.getElement(1), 13));
        elementList.add(ElementCard.createElementCharacter("Boy of Water", Elements.getElement(1), 14));
        elementList.add(ElementCard.createElementCharacter("Girl of Water", Elements.getElement(1), 15));
        elementList.add(ElementCard.createElementCharacter("Man of Water", Elements.getElement(1), 16));
        elementList.add(ElementCard.createElementCharacter("Woman of Water", Elements.getElement(1), 17));
        elementList.add(ElementCard.createElementCharacter("Elder Man of Water", Elements.getElement(1), 18));
        elementList.add(ElementCard.createElementCharacter("Elder Woman of Water", Elements.getElement(1), 19));
        
        // Create Wind Elements
        elementList.add(ElementCard.createElementCard("Lies", Elements.getElement(2), 1));
        elementList.add(ElementCard.createElementCard("Miracle", Elements.getElement(2), 2));
        elementList.add(ElementCard.createElementCard("Performance", Elements.getElement(2), 3));
        elementList.add(ElementCard.createElementCard("Sharing", Elements.getElement(2), 4));
        elementList.add(ElementCard.createElementCard("Accusation", Elements.getElement(2), 5));
        elementList.add(ElementCard.createElementCard("Celebration", Elements.getElement(2), 6));
        elementList.add(ElementCard.createElementCard("Disappearance", Elements.getElement(2), 7));
        elementList.add(ElementCard.createElementCard("Challenge", Elements.getElement(2), 8));
        elementList.add(ElementCard.createElementCard("Decay", Elements.getElement(2), 9));
        elementList.add(ElementCard.createElementCard("Prophesy", Elements.getElement(2), 10));
        elementList.add(ElementCard.createElementCard("Quest", Elements.getElement(2), 11));
        elementList.add(ElementCard.createElementCard("Metamorphosis", Elements.getElement(2), 12));
        
        // Create Wind Element Characters
        elementList.add(ElementCard.createElementCharacter("Baby of Wind", Elements.getElement(2), 13));
        elementList.add(ElementCard.createElementCharacter("Boy of Wind", Elements.getElement(2), 14));
        elementList.add(ElementCard.createElementCharacter("Girl of Wind", Elements.getElement(2), 15));
        elementList.add(ElementCard.createElementCharacter("Man of Wind", Elements.getElement(2), 16));
        elementList.add(ElementCard.createElementCharacter("Woman of Wind", Elements.getElement(2), 17));
        elementList.add(ElementCard.createElementCharacter("Elder Man of Wind", Elements.getElement(2), 18));
        elementList.add(ElementCard.createElementCharacter("Elder Woman of Wind", Elements.getElement(2), 19));
        
        // Create Earth Elements
        elementList.add(ElementCard.createElementCard("Weapon", Elements.getElement(3), 1));
        elementList.add(ElementCard.createElementCard("Treasure", Elements.getElement(3), 2));
        elementList.add(ElementCard.createElementCard("Vehicle", Elements.getElement(3), 3));
        elementList.add(ElementCard.createElementCard("Food", Elements.getElement(3), 4));
        elementList.add(ElementCard.createElementCard("Book", Elements.getElement(3), 5));
        elementList.add(ElementCard.createElementCard("Symbol", Elements.getElement(3), 6));
        elementList.add(ElementCard.createElementCard("Companion", Elements.getElement(3), 7));
        elementList.add(ElementCard.createElementCard("Tool", Elements.getElement(3), 8));
        elementList.add(ElementCard.createElementCard("Talisman", Elements.getElement(3), 9));
        elementList.add(ElementCard.createElementCard("Instrument", Elements.getElement(3), 10));
        elementList.add(ElementCard.createElementCard("Map", Elements.getElement(3), 11));
        elementList.add(ElementCard.createElementCard("Plant", Elements.getElement(3), 12));
        
        // Create Earth Element Characters
        elementList.add(ElementCard.createElementCharacter("Baby of Earth", Elements.getElement(3), 13));
        elementList.add(ElementCard.createElementCharacter("Boy of Earth", Elements.getElement(3), 14));
        elementList.add(ElementCard.createElementCharacter("Girl of Earth", Elements.getElement(3), 15));
        elementList.add(ElementCard.createElementCharacter("Man of Earth", Elements.getElement(3), 16));
        elementList.add(ElementCard.createElementCharacter("Woman of Earth", Elements.getElement(3), 17));
        elementList.add(ElementCard.createElementCharacter("Elder Man of Earth", Elements.getElement(3), 18));
        elementList.add(ElementCard.createElementCharacter("Elder Woman of Earth", Elements.getElement(3), 19));

        return elementList;
    }
    private ArrayList<ElementCard> createMythList(){
        
        mythList = new ArrayList<>();
        
        // Myth Card Creation
        mythList.add(ElementCard.createElementCard("Birth", Elements.getElement(4), 1));
        mythList.add(ElementCard.createElementCard("Magic", Elements.getElement(4), 2));
        mythList.add(ElementCard.createElementCard("Rules", Elements.getElement(4), 3));
        mythList.add(ElementCard.createElementCard("Lovers", Elements.getElement(4), 4));
        mythList.add(ElementCard.createElementCard("Falling", Elements.getElement(4), 5));
        mythList.add(ElementCard.createElementCard("Justice", Elements.getElement(4), 6));
        mythList.add(ElementCard.createElementCard("Isolation", Elements.getElement(4), 7));
        mythList.add(ElementCard.createElementCard("Luck", Elements.getElement(4), 8));
        mythList.add(ElementCard.createElementCard("Strength", Elements.getElement(4), 9));
        mythList.add(ElementCard.createElementCard("Perspective Change", Elements.getElement(4), 10));
        mythList.add(ElementCard.createElementCard("Death", Elements.getElement(4), 11));
        mythList.add(ElementCard.createElementCard("Cooperation", Elements.getElement(4), 12));
        mythList.add(ElementCard.createElementCard("Darkness", Elements.getElement(4), 13));
        mythList.add(ElementCard.createElementCard("Rebuilding", Elements.getElement(4), 14));
        mythList.add(ElementCard.createElementCard("Light", Elements.getElement(4), 15));
        mythList.add(ElementCard.createElementCard("Betrayal", Elements.getElement(4), 16));
        mythList.add(ElementCard.createElementCard("Success", Elements.getElement(4), 17));
        mythList.add(ElementCard.createElementCard("Judgement", Elements.getElement(4), 18));
        mythList.add(ElementCard.createElementCard("Completion", Elements.getElement(4), 19));
        
        return mythList;
    }
}

