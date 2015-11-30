/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes;

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

/**
 * REST Web Service
 *
 * @author c2learn
 */
@Path("4scribes/chaotic")
@Produces("application/json")
public class FourSChaotic {
    public static Random random = new Random();
    
    /**
     * Creates a new instance of FourSChaotic
     */
    public FourSChaotic() {
    }

    /**
     * Retrieves representation of an instance of com.idg.idgweb.FourSChaotic
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{players}/{cards}")
    public ArrayList<ArrayList<ElementCard>> getJSON(@PathParam("players") String num_players, @PathParam("cards") String cards) {
        //System.out.println("Chaotic Kate Initialization ... ");
        
		int players = Integer.parseInt(num_players);
        int num_cards = Integer.parseInt(cards);
        
		ArrayList<ArrayList<ElementCard>> gameHand = new ArrayList<>();
        ArrayList<ElementCard> cards_available = (ArrayList<ElementCard>) ElementList.getInstance().getElementList().clone();
        ArrayList<ElementCard> myths_available = (ArrayList<ElementCard>) ElementList.getInstance().getMythList().clone();
		
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
		
        for(int i = 0; i < players; i++){
            ArrayList<ElementCard> player_hand = new ArrayList<>();
            player_hand.add(myths_available.remove(random.nextInt(myths_available.size())));
			// add characters
			for(int j = 0; j < PlayerHand.charsPerHand; j++){
                ElementCard card_added = personalities_available.remove(random.nextInt(personalities_available.size()));
                ElementList.getInstance().incrementTimePlayed(card_added.getID());
                player_hand.add(card_added);
            }
			// add nonCharacters
            for(int j = 0; j < num_cards-PlayerHand.charsPerHand; j++){
                ElementCard card_added = nonPersonalities_available.remove(random.nextInt(nonPersonalities_available.size()));
                ElementList.getInstance().incrementTimePlayed(card_added.getID());
                player_hand.add(card_added);
            }
            gameHand.add(player_hand);
        }
        
        //System.out.println("Chaotic Kate Successful");
        return gameHand;
    }
	
	public static ArrayList<ElementCard> getRandomDefaultHand(int num_cards){
		ArrayList<ElementCard> result = new ArrayList<ElementCard>();
        ArrayList<ElementCard> cards_available = (ArrayList<ElementCard>) ElementList.getInstance().getElementList().clone();
        ArrayList<ElementCard> nonPersonalities_available = new ArrayList<ElementCard>();
        ArrayList<ElementCard> personalities_available = new ArrayList<ElementCard>();
		for(int i = 0; i < cards_available.size(); i++){
			if(cards_available.get(i).isCharacter()){ 
				personalities_available.add(cards_available.get(i));
			} else {
				nonPersonalities_available.add(cards_available.get(i));
			}
		}
		// add nonCharacters
        for(int j = 0; j < num_cards; j++){
            ElementCard card_added = nonPersonalities_available.remove(random.nextInt(nonPersonalities_available.size()));
            ElementList.getInstance().incrementTimePlayed(card_added.getID());
            result.add(card_added);
		}
		return result;
	}

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/{players}/{cards}")
    public ArrayList<ArrayList<ElementCard>> getJson(@PathParam("players") String num_players, @PathParam("cards") String cards, String str){
        
        //System.out.println("Custom - Chaotic Kate Initialization ...");
        
        // JSON Parsing Code - Lets Parse the Custom Cards
        JSONArray json = new JSONArray(str);
        
        ArrayList<JSONObject> json_list = new ArrayList<>();
        for(int i = 0; i < json.length(); i++){
            json_list.add(json.getJSONObject(i));
        }
        
        // Create a Card Object and add to the selected cards
        ArrayList<ElementCard> custom_cards = new ArrayList<>();
        for(int i = 0; i < json_list.size(); i++){
			// special: do not add characters
			boolean isCharacter = json_list.get(i).has("IsCharacter") && Boolean.parseBoolean(json_list.get(i).get("IsCharacter").toString());
			if(!isCharacter){
				custom_cards.add(ElementCard.createCustomElement(
					(int)json_list.get(i).get("CardID"), 
					(String)json_list.get(i).get("Name"), 
					Elements.getElement(5), 
					isCharacter,
					(int)json_list.get(i).get("CardNumber"))
				);
			}
        }

        int players = Integer.parseInt(num_players);
        int num_cards = Integer.parseInt(cards);
        
        ArrayList<ArrayList<ElementCard>> gameHand = new ArrayList<>();
        ArrayList<ElementCard> cards_available = (ArrayList<ElementCard>) ElementList.getInstance().getElementList().clone();
        ArrayList<ElementCard> myths_available = (ArrayList<ElementCard>) ElementList.getInstance().getMythList().clone();
        
		ArrayList<ElementCard> nonPersonalities_available = new ArrayList<ElementCard>();
        ArrayList<ElementCard> personalities_available = new ArrayList<ElementCard>();
		for(int i = 0; i < cards_available.size(); i++){
			if(cards_available.get(i).isCharacter()){ 
				personalities_available.add(cards_available.get(i));
			} else {
				nonPersonalities_available.add(cards_available.get(i));
			}
		}
		// add custom characters
		for(int i = 0; i < json_list.size(); i++){
			// special: do not add characters
			boolean isCharacter = json_list.get(i).has("IsCharacter") && Boolean.parseBoolean(json_list.get(i).get("IsCharacter").toString());
			if(isCharacter){
				personalities_available.add(ElementCard.createCustomElement(
					(int)json_list.get(i).get("CardID"), 
					(String)json_list.get(i).get("Name"), 
					Elements.getElement(5), 
					isCharacter,
					(int)json_list.get(i).get("CardNumber"))
				);
			}
        }
		//System.out.println("Characters to choose from: "+personalities_available.size());
			
        for(int i = 0; i < players; i++){
            ArrayList<ElementCard> player_hand = new ArrayList<>();
            player_hand.add(myths_available.remove(random.nextInt(myths_available.size())));
            // add characters
			for(int j = 0; j < PlayerHand.charsPerHand; j++){
                ElementCard card_added = personalities_available.remove(random.nextInt(personalities_available.size()));
                ElementList.getInstance().incrementTimePlayed(card_added.getID());
                player_hand.add(card_added);
				//System.out.println("Char added: "+card_added.getName());
            }
			// add nonCharacters
            for(int j = 0; j < num_cards-PlayerHand.charsPerHand; j++){
                if(custom_cards.size() > 0){
                    if(random.nextBoolean()){
                        ElementCard card_added = custom_cards.remove(random.nextInt(custom_cards.size()));
                        ElementList.getInstance().incrementTimePlayed(card_added.getID());
                        player_hand.add(card_added);
                    } else {
                        ElementCard card_added = nonPersonalities_available.remove(random.nextInt(nonPersonalities_available.size()));
                        ElementList.getInstance().incrementTimePlayed(card_added.getID());
                        player_hand.add(card_added);
                    }
                } else {
                    ElementCard card_added = nonPersonalities_available.remove(random.nextInt(nonPersonalities_available.size()));
                    ElementList.getInstance().incrementTimePlayed(card_added.getID());
                    player_hand.add(card_added);
                }
            }
            gameHand.add(player_hand);
        }
        //System.out.println("Custom - Chaotic Kate Successful");
        return gameHand;
        
    }
    
   
}
