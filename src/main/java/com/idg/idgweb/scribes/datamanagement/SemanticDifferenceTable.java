/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idg.idgweb.scribes.datamanagement;

import com.idg.idgweb.scribes.ElementCard;
import com.idg.idgweb.scribes.ElementList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import utils.ParameterCollection;
import wox.serial.Easy;

/**
 *
 * @author c2learn
 */
public class SemanticDifferenceTable {

    private static SemanticDifferenceTable instance = null;
    private HashMap<SemanticDiffKey, Double> semantic_diff_table = null;

    private int TOTAL_DIFF = 9025;
    private int current_diff = 0;

    private SemanticDifferenceTable() {
        initTable();
    }

    public static void initInstance(){
        if (instance == null) {
            //System.out.println("Initializing Semantc Difference Table ... ");
            instance = new SemanticDifferenceTable();
            //System.out.println("Initialization of Semantic Difference Table Successful");
        }
    }
    
    public static SemanticDifferenceTable getInstance() {
        if (instance == null) {
            //System.out.println("Initializing Semantc Difference Table ... ");
            instance = new SemanticDifferenceTable();
            //System.out.println("Initialization of Semantic Difference Table Successful");
        }
        return instance;
    }

    public static void destroyInstance(){
        instance = null;
    }
    
    private void initTable() {
        ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		//System.out.println("Path ---> " + pc.getString("4scribes_decks") + File.separator + "semantic_diff_table.xml");
        File f = new File(pc.getString("4scribes_decks") + File.separator + "semantic_diff_table.xml");
        if (f.exists()) {
            //System.out.println("Found and loading " + pc.getString("4scribes_decks") + File.separator + "semantic_diff_table.xml");
            semantic_diff_table = (HashMap<SemanticDiffKey, Double>) Easy.load(pc.getString("4scribes_decks") + File.separator + "semantic_diff_table.xml");
            setSemanticDiffValuesForCards();
        } else {
			//System.out.println("ERROR: could not find " + pc.getString("4scribes_decks") + File.separator + "semantic_diff_table.xml");
            createNewSemanticDiffHashTable();
            Easy.save(semantic_diff_table, pc.getString("4scribes_decks") + File.separator + "semantic_diff_table.xml");
        }
    }
	
	private void initTable(ArrayList<ElementCard> elementList, String deckID) {
        ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		//System.out.println("Path ---> " + pc.getString("4scribes_decks") + File.separator + deckID + "_semantic_diff_table.xml");
        //HashMap<SemanticDiffKey, Double> semantic_diff_table = new HashMap<SemanticDiffKey, Double>();
		File f = new File(pc.getString("4scribes_decks") + File.separator + deckID + "semantic_diff_table.xml");
        if (f.exists()) {
            HashMap<SemanticDiffKey, Double> semantic_diff_table = (HashMap<SemanticDiffKey, Double>) Easy.load(pc.getString("4scribes_decks") + File.separator + deckID + "semantic_diff_table.xml");
            setSemanticDiffValuesForCards();
        } else {
			HashMap<SemanticDiffKey, Double> semantic_diff_table = createNewSemanticDiffHashTable(elementList);
			Easy.save(semantic_diff_table, pc.getString("4scribes_decks")+ File.separator + deckID + "_semantic_diff_table.xml");
        }
    }
	public void saveTable(){
		
	}
	private HashMap<SemanticDiffKey, Double> createNewSemanticDiffHashTable(ArrayList<ElementCard> elementList) {
        HashMap<SemanticDiffKey, Double> result = new HashMap<SemanticDiffKey, Double>();
        int diff = 0;
        for (int i = 0; i < elementList.size(); i++) {
            ElementCard c1 = elementList.get(i);
            for (int j = 0; j < elementList.size(); j++) {
                ElementCard c2 = elementList.get(j);
                if (c1.getID() != c2.getID()) {
                    double sdiff = Double.parseDouble(SemanticDiff(c1.getName(), c2.getName()));
                    result.put(new SemanticDiffKey(c1.getID(), c2.getID()), sdiff);
                    c1.setSemanticDifferenceValue(c2.getSemanticDifferenceValue() + sdiff);
                    diff++;
                    //System.out.println("Percentage Complete = " + ( (diff / TOTAL_DIFF) * 100 ));
                }
            }
        }
		return result;
    }
	
	private void createNewSemanticDiffHashTable() {
        semantic_diff_table = new HashMap<>();
        int diff = 0;
        for (int i = 0; i < ElementList.getInstance().getElementList().size(); i++) {
            ElementCard c1 = ElementList.getInstance().getElementList().get(i);
            for (int j = 0; j < ElementList.getInstance().getElementList().size(); j++) {
                ElementCard c2 = ElementList.getInstance().getElementList().get(j);
                if (c1.getID() != c2.getID()) {
                    double sdiff = Double.parseDouble(SemanticDiff(c1.getName(), c2.getName()));
                    semantic_diff_table.put(new SemanticDiffKey(c1.getID(), c2.getID()), sdiff);
                    c1.setSemanticDifferenceValue(c2.getSemanticDifferenceValue() + sdiff);
                    diff++;
                    //System.out.println("Percentage Complete = " + ( (diff / TOTAL_DIFF) * 100 ));
                }
            }
        }
    }
    private void setSemanticDiffValuesForCards() {
        for (int i = 0; i < ElementList.getInstance().getElementList().size(); i++) {
            ElementCard c1 = ElementList.getInstance().getElementList().get(i);
            c1.setSemanticDifferenceValue(0);
            for (int j = 0; j < ElementList.getInstance().getElementList().size(); j++) {
                ElementCard c2 = ElementList.getInstance().getElementList().get(j);
                if (c1.getID() != c2.getID()) {
                    c1.setSemanticDifferenceValue(c1.getSemanticDifferenceValue() + semantic_diff_table.get(new SemanticDiffKey(c1.getID(), c2.getID())));          
                }
            }
        }
    }

    public void rebuildTable() {
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		createNewSemanticDiffHashTable();
        Easy.save(semantic_diff_table, pc.getString("4scribes_decks") + File.separator + "semantic_diff_table.xml");
    }

    public void locallySaveTable() {
        ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		Easy.save(semantic_diff_table, pc.getString("4scribes_decks") + File.separator + "semantic_diff_table.xml");
    }

    private String SemanticDiff(String word1, String word2){
		/*
		try { // Call Web Service Operation
            eu.c2learn.webservice.C2Learn_Service service = new eu.c2learn.webservice.C2Learn_Service();
            eu.c2learn.webservice.C2Learn port = service.getC2LearnPort();
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
		*/
		return null;
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
