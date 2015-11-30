/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes.custom;

import com.idg.idgweb.scribes.PlayerHand;
import com.idg.idgweb.scribes.ElementCard;

//import eu.c2learn.webservice.SemanticDistance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author c2learn
 */
@Path("cd4scribes/mad")
@Produces("application/json")
public class CustomFourSMad {
    
    
    private static final double PLAYING_WEIGHT = 1;
    
    /**
     * Creates a new instance of mad
     */
    public CustomFourSMad() {
    }
	
    /**
     * Calculation that relates a semantic difference value to the number of times a card is played.
     * @param semanticDiff
     * @return 
     */
    private double getRealSemanticValue(ElementCard card){
        double value = card.getSemanticDifferenceValue();
        //System.out.println("Card --> " + card.getName() + " ; " + value*Math.pow(Math.E, (-PLAYING_WEIGHT*card.getTimesPlayed())) );//Math.pow( (card.getSemanticDifferenceValue()*(PLAYING_WEIGHT)), card.getTimesPlayed() ));
        return value*Math.pow(Math.E, (-PLAYING_WEIGHT*card.getTimesPlayed()));
    }
    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/{players}/{cards}")
    public ArrayList<ArrayList<ElementCard>> getJson(@PathParam("players") String num_players, @PathParam("cards") String cards, String str){
        
        //System.out.println("Custom Card - Mad Scientist Initialization ... ");

		JSONObject obj = new JSONObject(str);
		String deckID = obj.getString("DeckID");
		
		CustomElementList cel = new CustomElementList(deckID);
		CustomSemanticTable cst = new CustomSemanticTable(cel.getElementList(),deckID);
		
        int players = Integer.parseInt(num_players);
        int num_cards = Integer.parseInt(cards);
        ArrayList<ArrayList<ElementCard>> gameHand = new ArrayList<>();

        for (int i = 0; i < players; i++) {
            gameHand.add(new ArrayList<ElementCard>());
        }
		
        ArrayList<ElementCard> cards_available = (ArrayList<ElementCard>) cel.getElementList().clone();
        ArrayList<ElementCard> myths_available = (ArrayList<ElementCard>) cel.getMythList().clone();

        // Give one Random Myth Card to Each Player
        for (int i = 0; i < gameHand.size(); i++) {
            gameHand.get(i).add(myths_available.remove(CustomFourSChaotic.random.nextInt(myths_available.size())));
        }
        
		ArrayList<ElementCard> nonPersonalities_available = new ArrayList<ElementCard>();
        ArrayList<ElementCard> personalities_available = new ArrayList<ElementCard>();
		for(int i = 0; i < cards_available.size(); i++){
			if(cards_available.get(i).isCharacter()){ 
				personalities_available.add(cards_available.get(i));
			} else {
				nonPersonalities_available.add(cards_available.get(i));
			}
		}
		//System.out.println("Characters to choose from: "+personalities_available.size());
		
        // Subtract the total from the number of current available cards
        ArrayList<ElementCard> selected_cards = new ArrayList<ElementCard>();
        int numTotal_cards = players * (num_cards-PlayerHand.charsPerHand);
        //System.out.println("Trying to find "+numTotal_cards+" more cards");
        
        // Card Selection Algorithm
        while (numTotal_cards > 0) {
           int max_diff_index = 0;
           for (int card_count = 0; card_count < nonPersonalities_available.size(); card_count++) {
               if (nonPersonalities_available.get(max_diff_index).getID() != nonPersonalities_available.get(card_count).getID()) {
                   if ( getRealSemanticValue(nonPersonalities_available.get(max_diff_index)) < getRealSemanticValue(nonPersonalities_available.get(card_count)) ) {
                       max_diff_index = card_count;
                   }
               }
           }
           //System.out.println("Index Value of " + max_diff_index);
           ElementCard card_added = nonPersonalities_available.remove(max_diff_index);
           cel.incrementTimePlayed(card_added.getID());
           selected_cards.add(card_added);
           numTotal_cards--;
        }
        
        // Suffle the cards selected
        Collections.shuffle(selected_cards, CustomFourSChaotic.random);

        // Round Robin Algorithm
        int current_player = 0;
        for(int i = 0; i < selected_cards.size(); i++){
            current_player = i % players;
            gameHand.get(current_player).add(selected_cards.get(i));
        }
		
		// add characters
		for(current_player = 0;current_player < players; current_player++){
			ElementCard card_added = personalities_available.remove(CustomFourSChaotic.random.nextInt(personalities_available.size()));
			cel.incrementTimePlayed(card_added.getID());
			gameHand.get(current_player).add(card_added);
		}
        
        //System.out.println("Custom Card - Mad Scientist Successful");
        
        return gameHand;
    }
}
