/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes;

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
@Path("4scribes/wise")
@Produces("application/json")
public class FourSWise {

    /**
     * Creates a new instance of FourSWise
     */
    public FourSWise() {
    }

    /**
     * Retrieves representation of an instance of com.idg.idgweb.scribes.FourSWise
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    @Path("/{players}/{cards}")
    public ArrayList<ArrayList<ElementCard>> getJson(@PathParam("players") String num_players, @PathParam("cards") String cards) {
        
        //System.out.println("Wise Oracle Initilization ... ");
        
        int players = Integer.parseInt(num_players);
        int num_cards = Integer.parseInt(cards);
        ArrayList<ArrayList<ElementCard>> gameHand = new ArrayList<>();
        
        for (int i = 0; i < players; i++) {
            gameHand.add(new ArrayList<ElementCard>());
        }

        ArrayList<ElementCard> cards_available = (ArrayList<ElementCard>) ElementList.getInstance().getElementList().clone();
        ArrayList<ElementCard> myths_available = (ArrayList<ElementCard>) ElementList.getInstance().getMythList().clone();
        
       // Give one Random Myth Card to Each Player
        for (int i = 0; i < gameHand.size(); i++) {
            gameHand.get(i).add(myths_available.remove(FourSChaotic.random.nextInt(myths_available.size())));
        }

        // Cards that will be dealt to each player
        ArrayList<ElementCard> selected_cards = new ArrayList<ElementCard>();
        int numTotal_cards = players * num_cards;
        
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
           ElementList.getInstance().incrementTimePlayed(card_added.getID());
           selected_cards.add(card_added);
           numTotal_cards--;
        }
        
        // Suffle the cards selected
        Collections.shuffle(selected_cards, FourSChaotic.random);

        // Round Robin Algorithm
        int current_player = 0;
        for(int i = 0; i < selected_cards.size(); i++){
            current_player = i % players;
            gameHand.get(current_player).add(selected_cards.get(i));
        }
        
        
        
//        // Round Robin Algorithm
//        int current_player = 0;
//        while (numTotal_cards > 0) {
//            current_player = numTotal_cards % players;
//            System.out.println("Current Player : " + current_player);
//            
//            int max_diff_index = 0;
//            for (int card_count = 0; card_count < cards_available.size(); card_count++) {
//                if (cards_available.get(max_diff_index).getID() != cards_available.get(card_count).getID()) {
//                    if (cards_available.get(max_diff_index).getTimesPlayed() < cards_available.get(card_count).getTimesPlayed()) {
//                        max_diff_index = card_count;
//                    }
//                }
//            }
//            System.out.println("Index Value of " + max_diff_index);
//            ElementCard card_added = cards_available.remove(max_diff_index);
//            ElementList.getInstance().incrementTimePlayed(card_added.getID());
//            gameHand.get(current_player).add(card_added);
//            numTotal_cards--;
//            
//        }
        
        //System.out.println("Wise Oracle Successful");
        
        return gameHand;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/{players}/{cards}")
    public ArrayList<ArrayList<ElementCard>> getJson(@PathParam("players") String num_players, @PathParam("cards") String cards, String str){
    
        //System.out.println("Wise Oracle Initilization ... ");
        
        int players = Integer.parseInt(num_players);
        int num_cards = Integer.parseInt(cards);
        ArrayList<ArrayList<ElementCard>> gameHand = new ArrayList<>();
        
        for (int i = 0; i < players; i++) {
            gameHand.add(new ArrayList<ElementCard>());
        }

        ArrayList<ElementCard> cards_available = (ArrayList<ElementCard>) ElementList.getInstance().getElementList().clone();
        ArrayList<ElementCard> myths_available = (ArrayList<ElementCard>) ElementList.getInstance().getMythList().clone();
        
       // Give one Random Myth Card to Each Player
        for (int i = 0; i < gameHand.size(); i++) {
            gameHand.get(i).add(myths_available.remove(FourSChaotic.random.nextInt(myths_available.size())));
        }

                // JSON Parsing Code - Lets Parse the Custom Cards
        JSONArray json = new JSONArray(str);
        
        ArrayList<JSONObject> json_list = new ArrayList<>();
        for(int i = 0; i < json.length(); i++){
            json_list.add(json.getJSONObject(i));
        }
        
        // Create a Card Object and add to the selected cards
        ArrayList<ElementCard> selected_cards = new ArrayList<ElementCard>();
        for(int i = 0; i < json_list.size(); i++){
            selected_cards.add(ElementCard.createCustomElement(
                    (int)json_list.get(i).get("CardID"), 
                    (String)json_list.get(i).get("Name"), 
                    Elements.getElement(5), 
                    Boolean.parseBoolean(json_list.get(i).get("IsCharacter").toString()),
                    (int)json_list.get(i).get("CardNumber")));
        }
        
        // Subtract the total from the number of current available cards
        int numTotal_cards = (players * num_cards) - selected_cards.size();
        
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
           ElementList.getInstance().incrementTimePlayed(card_added.getID());
           selected_cards.add(card_added);
           numTotal_cards--;
        }
        
        // Suffle the cards selected
        Collections.shuffle(selected_cards, FourSChaotic.random);

        // Round Robin Algorithm
        int current_player = 0;
        for(int i = 0; i < selected_cards.size(); i++){
            current_player = i % players;
            gameHand.get(current_player).add(selected_cards.get(i));
        }
        
        //System.out.println("Wise Oracle Successful");
        return gameHand;
    }
    
}
