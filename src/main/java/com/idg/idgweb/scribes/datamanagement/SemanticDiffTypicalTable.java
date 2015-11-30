/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes.datamanagement;

import com.idg.idgweb.scribes.ElementCard;
import com.idg.idgweb.scribes.ElementList;
import com.idg.idgweb.scribes.FourSChaotic;
import java.io.File;
import java.util.ArrayList;
import utils.ParameterCollection;
import wox.serial.Easy;

/**
 *
 * @author c2learn
 */
public class SemanticDiffTypicalTable {
    
    private static SemanticDiffTypicalTable instance = null;
    
    private ArrayList<ElementCard> typical_hand;
    private String name_typical_hand;
    private int ID_typical_hand;
    
    private static final int TOTAL_TYPICAL_NUM = 6;
    
    private static final double PLAYING_WEIGHT = 1;
    
    private SemanticDiffTypicalTable(String filename){
		loadTypicalHand(filename);
		/*
        typical_hand = new ArrayList<>();
        for(int i = 0; i < TOTAL_TYPICAL_NUM; i++){
            typical_hand.add(ElementList.getInstance().getElementList().get(FourSChaotic.random.nextInt(ElementList.getInstance().getElementList().size())));
        }
        
        System.out.println("THE TYPICAL HAND IS ");
        for(ElementCard c: typical_hand){
            System.out.println(c.getName());
        }
		*/
    }
	
	private void loadTypicalHand(String filename){
		// load typical set
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		//System.out.println("Loading typical set from "+pc.getString("4scribes_decks") + File.separator + filename);
		File f = new File(pc.getString("4scribes_decks") + File.separator + filename);
        if(f.exists()){ 
			//System.out.println("File "+filename+" FOUND!"); 
		} else {
			//ArrayList<ElementCard> toSave = FourSChaotic.getRandomDefaultHand(4);	// default 4
			ArrayList<ElementCard> toSave = new ArrayList<ElementCard>();
			toSave.add(ElementList.getInstance().getElementByName("Eureka!"));
			toSave.add(ElementList.getInstance().getElementByName("Learn"));
			toSave.add(ElementList.getInstance().getElementByName("Sharing"));
			toSave.add(ElementList.getInstance().getElementByName("Companion"));
			Easy.save(toSave,pc.getString("4scribes_decks") + File.separator + filename);
			//System.out.println("New "+filename+" CREATED!");
		}
		typical_hand = (ArrayList<ElementCard>)(Easy.load(pc.getString("4scribes_decks") + File.separator + filename));
		//System.out.println("typical_hand contains "+typical_hand.size()+" cards.");
	}
    
    public static SemanticDiffTypicalTable getInstance() {
        if (instance == null) {
            //System.out.println("Creating new Typical Hand Set ... ");
            instance = new SemanticDiffTypicalTable("scribesTypical.xml");
            //System.out.println("Successfully Created!");
        }
        return instance;
    }
    
    public double getTypicalityValue(ElementCard element){
        double aggregate_value = 0;
        for(ElementCard c: typical_hand){
            if(element.getID() != c.getID()){
                aggregate_value += SemanticDifferenceTable.getInstance().getValue(element.getID(), c.getID());

                // For Debug!
                //System.out.println("Difference Value Between " + element.getName() + " and " + c.getName() 
                //        + " = " + SemanticDifferenceTable.getInstance().getValue(element.getID(), c.getID()));
            }
        }
        return aggregate_value;
    }
    
    public double getProgressiveValueAndTimesPlayed(ElementCard card){
        double value = getTypicalityValue(card);
        //System.out.println("Card --> " + card.getName() + " with value " + value + " ; " + Math.pow(value*(1-PLAYING_WEIGHT), card.getTimesPlayed()) );
        return Math.pow(value*(1-PLAYING_WEIGHT), card.getTimesPlayed());
    }
    
    public double getTypicalValueAndTimesPlayed(ElementCard card){
        double value = getTypicalityValue(card);
        //System.out.println("Card --> " + card.getName() + " with value " + value + " ; " + value*Math.pow(Math.E, (-PLAYING_WEIGHT*card.getTimesPlayed()) ) ); //Math.pow(value*(1-PLAYING_WEIGHT), card.getTimesPlayed()) );
        //return Math.pow(value*(1-PLAYING_WEIGHT), card.getTimesPlayed());
        return value*Math.pow(Math.E, (-PLAYING_WEIGHT*card.getTimesPlayed()));
		//return value*Math.max(1,card.getTimesPlayed());
    }
    
    public double getTypicalTomValueAndTimesPlayed(ElementCard card){
        double value = getTypicalityValue(card);
        //System.out.println("Typical Value of " + card.getName() + " = " + value);
        //System.out.println("Card --> " + card.getName() + " with value " + value + " ; " + value*card.getTimesPlayed()); //Math.pow(value*(1-PLAYING_WEIGHT), card.getTimesPlayed()) );
        //return Math.pow(value*(1-PLAYING_WEIGHT), card.getTimesPlayed());
        return value*Math.max(1,card.getTimesPlayed());
    }
}
