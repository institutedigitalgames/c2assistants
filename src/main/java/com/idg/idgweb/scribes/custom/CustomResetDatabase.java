/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes.custom;

import com.idg.idgweb.scribes.ElementCard;

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
import utils.ParameterCollection;
import wox.serial.Easy;

/**
 * REST Web Service
 *
 * @author c2learn
 */
@Path("cd4scribes/resetDB")
@Produces("application/json")
public class CustomResetDatabase {
    public CustomResetDatabase() {
    }
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean getJSON(String inputJson){
		JSONObject obj = new JSONObject(inputJson);
		String deckID = obj.getString("DeckID");
		
        //System.out.println("Resetting database of deck "+deckID+"... ");
		
		ArrayList<ElementCard> elementList = null;//createElementList();
		ArrayList<ElementCard> mythList = null;//createMythList();
		
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		
        // Create the Elements List
		//System.out.println("Element List Path ---> " + pc.getString("4scribes_decks") + File.separator + deckID+"_elementList.xml");
        File f = new File(pc.getString("4scribes_decks") + File.separator + deckID+"_elementList.xml");
        if (f.exists()) {
            elementList = (ArrayList<ElementCard>) Easy.load(pc.getString("4scribes_decks") + File.separator + deckID+"_elementList.xml");
			for(int i=0;i<elementList.size();i++){ elementList.get(i).resetPlayed(); }
			Easy.save(elementList, pc.getString("4scribes_decks") + File.separator + deckID+"_elementList.xml");
        }
        
        // Create the Myth List
        //System.out.println("Myth Element List Path ---> " + pc.getString("4scribes_decks") + File.separator + "mythElementList.xml");
        f = new File(pc.getString("4scribes_decks") + File.separator + "mythElementList.xml");
        if (f.exists()) {
            mythList = (ArrayList<ElementCard>) Easy.load(pc.getString("4scribes_decks") + File.separator + "mythElementList.xml");
			for(int i=0;i<mythList.size();i++){ mythList.get(i).resetPlayed(); }
			Easy.save(mythList, pc.getString("4scribes_decks") + File.separator + "mythElementList.xml");
        }
		/*
		// FIX THE INSTANCE TOO
		for(int i=0;i<ElementList.getInstance().getElementList().size();i++){ ElementList.getInstance().getElementList().get(i).resetPlayed(); }
		for(int i=0;i<ElementList.getInstance().getMythList().size();i++){ ElementList.getInstance().getMythList().get(i).resetPlayed(); }
		*/
        return true;
    }
}