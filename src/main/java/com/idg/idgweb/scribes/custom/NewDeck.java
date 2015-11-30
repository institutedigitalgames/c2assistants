/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes.custom;

import com.idg.idgweb.scribes.ElementCard;
import com.idg.idgweb.scribes.Elements;

import java.io.File;

import java.util.ArrayList;
import java.util.Random;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import wox.serial.Easy;

/**
 * REST Web Service
 *
 * @author c2learn
 */
@Path("cd4scribes/newDeck")
@Produces("application/json")
public class NewDeck {
    public NewDeck() {
    }
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean getJson(String inputJson){
        //System.out.println("Creating a new Deck ... ");
		//System.out.println("input data: "+inputJson);
		JSONObject obj = new JSONObject(inputJson);
		String deckID = obj.getString("DeckID");
		JSONArray json_cardList = obj.getJSONArray("Cards");
		//System.out.println("I have "+json_cardList.length()+" cards");
		ArrayList<ElementCard> elementList = new ArrayList<ElementCard>();
		ArrayList<ElementCard> mythList = new ArrayList<ElementCard>();
		for(int i=0;i<json_cardList.length();i++){
			boolean isCharacter = json_cardList.getJSONObject(i).has("IsCharacter") && Boolean.parseBoolean(json_cardList.getJSONObject(i).getString("IsCharacter"));
			boolean isMyth = json_cardList.getJSONObject(i).getInt("CardID")>=77 && json_cardList.getJSONObject(i).getInt("CardID")<=95;
			if(isMyth){
				//System.out.println("Adding myth card: "+json_cardList.getJSONObject(i).getString("Name"));
				mythList.add(ElementCard.createCustomElement(
						json_cardList.getJSONObject(i).getInt("CardID"),
						json_cardList.getJSONObject(i).getString("Name"), 
						"MYTH", 
						isCharacter,
						json_cardList.getJSONObject(i).getInt("CardNumber")
				));
			} else {
				//System.out.println("Adding normal card: "+json_cardList.getJSONObject(i).getString("Name"));
				elementList.add(ElementCard.createCustomElement(
						json_cardList.getJSONObject(i).getInt("CardID"),
						json_cardList.getJSONObject(i).getString("Name"), 
						"Custom", 
						isCharacter,
						json_cardList.getJSONObject(i).getInt("CardNumber")
				));
			}
		}
		CustomElementList.saveCustomElementList(elementList,deckID);
		CustomElementList.saveCustomMythList(mythList,deckID);
		CustomSemanticTable.rebuildTable(elementList,deckID);
		CustomElementList.saveCustomElementList(elementList,deckID);
		
		// typical hand should always be AFTER the elementcards are created
		if(obj.has("TypicalHand")){
			JSONArray json_typicalHand = obj.getJSONArray("TypicalHand");
			ArrayList<String> typicalHand = new ArrayList<String>();
			for(int i=0;i<json_typicalHand.length();i++){ typicalHand.add(json_typicalHand.getString(i)); }
			CustomSemanticTypicalTable.saveTypicalHand(deckID, typicalHand);
		}
		
		return true;
		// now we need to compute semantic difference
		
	}
	
	public static boolean isMyth(String cardName){
		ArrayList<ElementCard> mythList = createMythList();
		for(int i=0;i<mythList.size();i++){
			if(cardName.equalsIgnoreCase(mythList.get(i).getName())){
				return true;
			}
		}
		return false;
	}
	
	private static ArrayList<ElementCard> createMythList(){
        ArrayList<ElementCard> mythList = new ArrayList<ElementCard>();
        
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