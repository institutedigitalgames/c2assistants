/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.iconoscope;

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
/*
import javax.json.*;
import javax.json.JsonReader;
*/
import java.io.StringReader;

import org.json.JSONObject;
import org.json.JSONArray;

/**
 * REST Web Service
 *
 * @author c2learn
 */
@Path("icono/chaotic")
@Produces("application/json")
public class IconoChaotic {

    /**
     * Creates a new instance of FourSChaotic
     */
    public IconoChaotic() {
    }

    /**
     * Retrieves representation of an instance of com.idg.idgweb.FourSChaotic
     * @return an instance of java.lang.String
     */
	/*
    @GET
    @Path("/{concepts}")
    public ArrayList<Canvas> getJSON(@PathParam("concepts") String num_concepts) {
     */   
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    //@Path("/{concepts}")
    public ArrayList<Canvas> getJson(String inputJson) {
		
        //System.out.println("Chaotic Kate Initialization...");
        
		InputData parsed = InputData.fromJSON(new JSONObject(inputJson));
		Canvas parsed_canvas = parsed.getCanvas();
		
		int concepts=4;
		//System.out.println("Creating "+concepts+" concepts...");
        
		ArrayList<Canvas> result = new ArrayList<Canvas>();
		for(int i=0;i<concepts;i++){
			result.add(parsed_canvas.createCopy());
			//System.out.println("cloned concept = "+result.get(i).concept);
		}
		
		final int maxMutations = 10;
		
		// and then mutate them!
		for(int i=0;i<result.size();i++){
			for(int m=0;m<maxMutations;m++){ 
				result.get(i).mutate();
			}
			result.get(i).ID = "Canvas "+i;
			///System.out.println("mutated concept = "+result.get(i).concept);
		}
		//System.out.println("Chaotic Kate has made "+result.size()+" results!");
	
        return result;
    }

    /**
     * PUT method for updating or creating an instance of FourSChaotic
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putXml(String content) {
    }
}
