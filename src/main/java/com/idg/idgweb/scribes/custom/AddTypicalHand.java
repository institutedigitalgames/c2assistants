package com.idg.idgweb.scribes.custom;

import java.io.File;
import java.util.ArrayList;

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
@Path("cd4scribes/addTypical")
@Produces("application/json")
public class AddTypicalHand {
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    //@Path("/{concepts}")
    public String getJson(String inputJson) {
		//System.out.println("Adding a typical hand ... ");
		//System.out.println("input data: "+inputJson);
		JSONObject obj = new JSONObject(inputJson);
		String deckID = obj.getString("DeckID");
		// typical hand should always be AFTER the elementcards are created
		if(obj.has("TypicalHand")){
			JSONArray json_typicalHand = obj.getJSONArray("TypicalHand");
			ArrayList<String> typicalHand = new ArrayList<String>();
			String result = "";
			for(int i=0;i<json_typicalHand.length();i++){ 
				String name = json_typicalHand.getString(i);
				//String wname = name.replaceAll("\\s+","");
				typicalHand.add(name);
			}
			CustomSemanticTypicalTable.saveTypicalHand(deckID, typicalHand);
			CustomSemanticTypicalTable ctt = new CustomSemanticTypicalTable(deckID);
			result += "total cards in hand: "+typicalHand.size() + " but "+ctt.getTypicalHandSize()+" { ";
			for(int i=0;i<typicalHand.size();i++){result+=" \""+typicalHand.get(i)+"\", "; }
			result+=" } but { ";
			for(int i=0;i<ctt.getTypicalHandSize();i++){result+=" \""+ctt.getTypicalHand().get(i).getName()+"\", "; }
			result+=" }";
			return result;
		}
		
		return "no TypicalHand found";
	}
}
