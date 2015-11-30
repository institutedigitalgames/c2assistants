package com.idg.idgweb.iconoscope;

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
import org.json.JSONObject;

//import java.security.MessageDigest;
//import java.math.BigInteger;

import wox.serial.Easy;

import utils.ParameterCollection;

/**
 * REST Web Service
 *
 * @author c2learn
 */
@Path("icono/addTypical")
@Produces("application/json")
public class AddTypicalIcon {
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    //@Path("/{concepts}")
    public boolean getJson(String inputJson) {
		//System.out.println("Adding a new Typical Icon...");
        
		InputData parsed = InputData.fromJSON(new JSONObject(inputJson));
		String conceptName = parsed.getApp().SelectedWord;
		Canvas parsed_canvas = parsed.getCanvas();
		
		// simplest solution: just replace whatever was there
		
		// load typical concept
		ParameterCollection pc = new ParameterCollection(ParameterCollection.parFileLoc);
		String targetWord = parsed.getApp().SelectedWord;
		String[] components = targetWord.split("/");
		targetWord = components[0];
		
		Easy.save(parsed_canvas,pc.getString("iconoscope_typical") + File.separator + targetWord+".xml");
		
		return true;
	}
}
