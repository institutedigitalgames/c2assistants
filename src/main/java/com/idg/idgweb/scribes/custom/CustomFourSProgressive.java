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
@Path("cd4scribes/progressive")
@Produces("application/json")
public class CustomFourSProgressive {

    /**
     * Creates a new instance of Atypical
     */
    public CustomFourSProgressive() {
    }

	protected int getMaxDistanceIndex(CustomSemanticTypicalTable cstt,CustomSemanticTable cst,ArrayList<ElementCard> cardSet){
		int max_diff_index = 0;
		double current_max = -Double.MAX_VALUE;
		for (int card_count = 0; card_count < cardSet.size(); card_count++) {
			if (cardSet.get(max_diff_index).getID() != cardSet.get(card_count).getID()) {
				double value = cstt.getTypicalValueAndTimesPlayed(cardSet.get(card_count),cst);
				//System.out.println("VALUE IS = " + value + " CURRENT MAX = " + current_max);
				// MAXIMIZE SEMANTIC DIFFERENCE
				if(value > current_max){
					max_diff_index = card_count;
					current_max = value;
				}
			}
		}
		return max_diff_index;
	}
	
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/{players}/{cards}")
    public ArrayList<ArrayList<ElementCard>> getJson(@PathParam("players") String num_players, @PathParam("cards") String cards, String str){
        
        //System.out.println("Progressive Petra Initilization ... ");
        
		JSONObject obj = new JSONObject(str);
		String deckID = obj.getString("DeckID");
		
		CustomElementList cel = new CustomElementList(deckID);
		CustomSemanticTable cst = new CustomSemanticTable(cel.getElementList(),deckID);
		CustomSemanticTypicalTable cstt = new CustomSemanticTypicalTable(deckID);
		
        int players = Integer.parseInt(num_players);
        int num_cards = Integer.parseInt(cards);
        ArrayList<ArrayList<ElementCard>> gameHand = new ArrayList<>();
        
        for (int i = 0; i < players; i++) {
            gameHand.add(new ArrayList<ElementCard>());
        }

        ArrayList<ElementCard> cards_available = (ArrayList<ElementCard>) cel.getElementList().clone();
        ArrayList<ElementCard> myths_available = (ArrayList<ElementCard>) cel.getMythList().clone();

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
		
        // Subtract the total from the number of current available cards
        ArrayList<ElementCard> selected_cards = new ArrayList<ElementCard>();
        int numTotal_cards = players * (num_cards-PlayerHand.charsPerHand);
        //System.out.println("Trying to find "+numTotal_cards+" more cards");
        
        // Card Selection Algorithm
        while (numTotal_cards > 0) {
            int max_diff_index = getMaxDistanceIndex(cstt,cst,nonPersonalities_available);
			//System.out.println(	"Index Value of " + nonPersonalities_available.get(max_diff_index).getName() +
			//					" and Typical Value = " + cstt.getTypicalValueAndTimesPlayed(nonPersonalities_available.get(max_diff_index),cst));
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
		
        cel.saveElementsLists(deckID);	// don't forget to save increments
		
        //System.out.println("Progressive Petra Successful");
        return gameHand;
    }
      
}
