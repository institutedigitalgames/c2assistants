/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes.custom;

import com.idg.idgweb.scribes.ElementCard;

import java.io.File;
import java.util.ArrayList;
import utils.ParameterCollection;
import wox.serial.Easy;

/**
 *
 * @author c2learn
 */
public class CustomSemanticTypicalTable {
    private ArrayList<ElementCard> typical_hand;
    
    private static final double PLAYING_WEIGHT = 1;
    
    public CustomSemanticTypicalTable(String deckID){
		loadTypicalHand(deckID);
    }
	
	private void loadTypicalHand(String deckID){
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		// load typical set
		//System.out.println("Loading typical set from "+pc.getString("4scribes_decks") + File.separator + deckID+"_typicalHand.xml");
		File f = new File(pc.getString("4scribes_decks") + File.separator + deckID+"_typicalHand.xml");
        if(f.exists()){ 
			//System.out.println("File "+deckID+"_typicalHand.xml"+" FOUND!"); 
		} else {
			ArrayList<ElementCard> toSave = CustomFourSChaotic.getRandomDefaultHand(deckID,4);	// default 4 (doesn't affect number of cards of actual deck)
			/*
			ArrayList<ElementCard> toSave = new ArrayList<ElementCard>();
			toSave.add(ElementList.getInstance().getElementByName("Eureka!"));
			toSave.add(ElementList.getInstance().getElementByName("Learn"));
			toSave.add(ElementList.getInstance().getElementByName("Sharing"));
			toSave.add(ElementList.getInstance().getElementByName("Companion"));
			*/
			Easy.save(toSave,pc.getString("4scribes_decks") + File.separator + deckID+"_typicalHand.xml");
			//System.out.println("New "+deckID+" hand CREATED!");
		}
		typical_hand = (ArrayList<ElementCard>)(Easy.load(pc.getString("4scribes_decks") + File.separator + deckID+"_typicalHand.xml"));
		//System.out.println("typical_hand contains "+typical_hand.size()+" cards.");
	}
	
	public static void saveTypicalHand(String deckID,ArrayList<String> cardNames){
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		ArrayList<ElementCard> toSave = new ArrayList<ElementCard>();
		CustomElementList cel = new CustomElementList(deckID);
		for(int i=0;i<cardNames.size();i++){
			ElementCard ec = cel.getElementByName(cardNames.get(i));
			if(ec!=null){ toSave.add(ec); }	
		}
		Easy.save(toSave,pc.getString("4scribes_decks") + File.separator + deckID+"_typicalHand.xml");
		//System.out.println("New "+deckID+" hand CREATED!");
	}
    
    public double getTypicalityValue(ElementCard element,CustomSemanticTable cst){
        double aggregate_value = 0;
        for(ElementCard c: typical_hand){
            if(element.getID() != c.getID()){
                aggregate_value += cst.getValue(element.getID(), c.getID());

                // For Debug!
                //System.out.println("Difference Value Between " + element.getName() + " and " + c.getName() 
                //        + " = " + cst.getValue(element.getID(), c.getID()));
            }
        }
        return aggregate_value;
    }
    
    public double getProgressiveValueAndTimesPlayed(ElementCard card,CustomSemanticTable cst){
        double value = getTypicalityValue(card,cst);
        //System.out.println("Card --> " + card.getName() + " with value " + value + " ; " + Math.pow(value*(1-PLAYING_WEIGHT), card.getTimesPlayed()) );
        return Math.pow(value*(1-PLAYING_WEIGHT), card.getTimesPlayed());
    }
    
    public double getTypicalValueAndTimesPlayed(ElementCard card,CustomSemanticTable cst){
        double value = getTypicalityValue(card,cst);
        //System.out.println("Card --> " + card.getName() + " with value " + value + " ; " + value*Math.pow(Math.E, (-PLAYING_WEIGHT*card.getTimesPlayed()) ) ); //Math.pow(value*(1-PLAYING_WEIGHT), card.getTimesPlayed()) );
        //return Math.pow(value*(1-PLAYING_WEIGHT), card.getTimesPlayed());
        return value*Math.pow(Math.E, (-PLAYING_WEIGHT*card.getTimesPlayed()));
		//return value*Math.max(1,card.getTimesPlayed());
    }
    
    public double getTypicalTomValueAndTimesPlayed(ElementCard card,CustomSemanticTable cst){
        double value = getTypicalityValue(card,cst);
        //System.out.println("Typical Value of " + card.getName() + " = " + value);
        //System.out.println("Card --> " + card.getName() + " with value " + value + " ; " + value*card.getTimesPlayed()); //Math.pow(value*(1-PLAYING_WEIGHT), card.getTimesPlayed()) );
        //return Math.pow(value*(1-PLAYING_WEIGHT), card.getTimesPlayed());
        return value*Math.max(1,card.getTimesPlayed());
    }
	
	public int getTypicalHandSize(){ return typical_hand.size(); }
	public ArrayList<ElementCard> getTypicalHand(){ return typical_hand; }
}
