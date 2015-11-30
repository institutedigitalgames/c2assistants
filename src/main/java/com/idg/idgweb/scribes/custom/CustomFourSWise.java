/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes.custom;

import com.idg.idgweb.scribes.PlayerHand;
import com.idg.idgweb.scribes.ElementCard;

import java.util.ArrayList;
import java.util.Collections;
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
@Path("cd4scribes/wise")
@Produces("application/json")
public class CustomFourSWise {

    /**
     * Creates a new instance of FourSWise
     */
    public CustomFourSWise() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/{players}/{cards}")
    public ArrayList<ArrayList<ElementCard>> getJson(@PathParam("players") String num_players, @PathParam("cards") String cards, String str){
    
        //System.out.println("Wise Oracle Initilization ... ");
        
		JSONObject obj = new JSONObject(str);
		String deckID = obj.getString("DeckID");
		
		CustomElementList cel = new CustomElementList(deckID);
		//CustomSemanticTable cst = new CustomSemanticTable(cel.getElementList(),deckID);
		
        int players = Integer.parseInt(num_players);
        int num_cards = Integer.parseInt(cards);
        ArrayList<ArrayList<ElementCard>> gameHand = new ArrayList<>();
        
        for (int i = 0; i < players; i++) {
            gameHand.add(new ArrayList<ElementCard>());
        }

        ArrayList<ElementCard> cards_available = (ArrayList<ElementCard>) cel.getElementList().clone();
        ArrayList<ElementCard> myths_available = (ArrayList<ElementCard>) cel.getMythList().clone();
       
		// divide personalities and non-personalities
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
		
		// Give one Random Myth Card to Each Player
        for (int i = 0; i < gameHand.size(); i++) {
            gameHand.get(i).add(myths_available.remove(CustomFourSChaotic.random.nextInt(myths_available.size())));
        }

        // Cards that will be dealt to each player
        ArrayList<ElementCard> selected_cards = new ArrayList<ElementCard>();
        int numTotal_cards = players * (num_cards-PlayerHand.charsPerHand);
        
        // Card Selection Algorithm
        while (numTotal_cards > 0) {
           int max_diff_index = 0;
           for (int card_count = 0; card_count < cards_available.size(); card_count++) {
               if (cards_available.get(max_diff_index).getID() != cards_available.get(card_count).getID()) {
                   if ( cards_available.get(max_diff_index).getTimesPlayed() < cards_available.get(card_count).getTimesPlayed() ) {
                       max_diff_index = card_count;
                   }
               }
           }
           //System.out.println("Index Value of " + max_diff_index);
           ElementCard card_added = cards_available.remove(max_diff_index);
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
		
        cel.saveElementsLists(deckID);	// don't forget to save increments
		
        //System.out.println("Wise Oracle Successful");
        return gameHand;
    }
    
}
