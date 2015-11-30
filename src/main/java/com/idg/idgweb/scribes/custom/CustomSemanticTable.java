package com.idg.idgweb.scribes.custom;

import com.idg.idgweb.scribes.datamanagement.SemanticDiffKey;
import com.idg.idgweb.scribes.ElementCard;

import eu.semantic.webservice.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import utils.ParameterCollection;

import wox.serial.Easy;

public class CustomSemanticTable {
	private HashMap<SemanticDiffKey, Double> semantic_diff_table = null;

    //private int TOTAL_DIFF = 9025;
    private int current_diff = 0;

    public CustomSemanticTable(ArrayList<ElementCard> elementList, String deckID) {
        initTable(elementList, deckID);
    }
	private void initTable(ArrayList<ElementCard> elementList, String deckID) {
        ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		
		//System.out.println("Path ---> " + pc.getString("4scribes_decks") + File.separator + deckID + "_semantic_diff_table.xml");
        //HashMap<SemanticDiffKey, Double> semantic_diff_table = new HashMap<SemanticDiffKey, Double>();
		File f = new File(pc.getString("4scribes_decks") + File.separator + deckID + "_semantic_diff_table.xml");
        if (f.exists()) {
            semantic_diff_table = (HashMap<SemanticDiffKey, Double>) Easy.load(pc.getString("4scribes_decks") + File.separator + deckID + "_semantic_diff_table.xml");
            setSemanticDiffValuesForCards(elementList);
        } else {
			semantic_diff_table = createNewSemanticDiffHashTable(elementList);
			Easy.save(semantic_diff_table, pc.getString("4scribes_decks") + File.separator + deckID + "_semantic_diff_table.xml");
			//setSemanticDiffValuesForCards(elementList);
        }
    }
	public void saveTable(String deckID){
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		Easy.save(semantic_diff_table, pc.getString("4scribes_decks") + File.separator + deckID + "_semantic_diff_table.xml");
	}
	static public void rebuildTable(ArrayList<ElementCard> elementList, String deckID){
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		HashMap<SemanticDiffKey, Double> semantic_diff_table = createNewSemanticDiffHashTable(elementList);
		Easy.save(semantic_diff_table, pc.getString("4scribes_decks") + File.separator + deckID + "_semantic_diff_table.xml");
	}
	static private HashMap<SemanticDiffKey, Double> createNewSemanticDiffHashTable(ArrayList<ElementCard> elementList) {
        HashMap<SemanticDiffKey, Double> result = new HashMap<SemanticDiffKey, Double>();
        int diff = 0;
        for (int i = 0; i < elementList.size(); i++) {
            ElementCard c1 = elementList.get(i);
            for (int j = 0; j < elementList.size(); j++) {
                ElementCard c2 = elementList.get(j);
                if (c1.getID() != c2.getID()) {
                    double sdiff = Double.parseDouble(SemanticDiff(c1.getName(), c2.getName()));
                    result.put(new SemanticDiffKey(c1.getID(), c2.getID()), sdiff);
                    c1.setSemanticDifferenceValue(c1.getSemanticDifferenceValue() + sdiff);
                    diff++;
                    //System.out.println("Percentage Complete = " + ( (diff / TOTAL_DIFF) * 100 ));
                }
				//System.out.println("Sleeping...");
				try{ Thread.sleep(100); }catch(InterruptedException ie){ System.out.println(ie.getMessage()); }
            }
        }
		return result;
    }

    private void setSemanticDiffValuesForCards(ArrayList<ElementCard> elementList) {
        for (int i = 0; i < elementList.size(); i++) {
            ElementCard c1 = elementList.get(i);
            c1.setSemanticDifferenceValue(0);
            for (int j = 0; j < elementList.size(); j++) {
                ElementCard c2 = elementList.get(j);
                if (c1.getID() != c2.getID()) {
                    c1.setSemanticDifferenceValue(c1.getSemanticDifferenceValue() + semantic_diff_table.get(new SemanticDiffKey(c1.getID(), c2.getID())));          
                }
            }
        }
    }
    static private String SemanticDiff(String word1, String word2){
		try { // Call Web Service Operation
            //eu.c2learn.webservice.C2Learn_Service service = new eu.c2learn.webservice.C2Learn_Service();
            //eu.c2learn.webservice.C2Learn port = service.getC2LearnPort();
			eu.semantic.webservice.Semantic service = new eu.semantic.webservice.Semantic();
			eu.semantic.webservice.SemDistance port = service.getSemDistancePort();
			
            // TODO initialize WS operation arguments here
            java.lang.String firstWord = word1;
            java.lang.String secondWord = word2;
            java.lang.String language = "en";
            // TODO process result here
            java.lang.String result = port.semanticDistance(firstWord, secondWord, language);
           
			//System.out.println("Result = "+result);
            return result;
        } catch (Exception ex) {
            //System.out.println("ERROR with word: " + word1 + " and word: " + word2);
            return null;
            // TODO handle custom exceptions here
        }
    }
    
    public double getValue(SemanticDiffKey key){
        return semantic_diff_table.get(key);
    }
    
    public double getValue(int ElementID_1, int ElementID_2){
        return semantic_diff_table.get(new SemanticDiffKey(ElementID_1, ElementID_2));
    }
    
    public void putValue(SemanticDiffKey key, double value){
        semantic_diff_table.put(key, value);
    }
}

