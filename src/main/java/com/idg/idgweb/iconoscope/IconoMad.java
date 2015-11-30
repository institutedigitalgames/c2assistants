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
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author c2learn
 */
@Path("icono/mad")
@Produces("application/json")
public class IconoMad {
    /**
     * Creates a new instance of FourSChaotic
     */
    public IconoMad() {
    }

    /**
     * Retrieves representation of an instance of com.idg.idgweb.FourSChaotic
     * @return an instance of java.lang.String
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    //@Path("/{concepts}")
    public ArrayList<Canvas> getJson(String inputJson) {
		
        //System.out.println("Mad Scientist Initialization...");
        
		InputData parsed = InputData.fromJSON(new JSONObject(inputJson));
		Canvas parsed_canvas = parsed.getCanvas();
		
		int populationSize = 10;
		//System.out.println("Creating "+populationSize+" individuals...");
       
		GeneticAlgorithm ga = new GeneticAlgorithm(parsed_canvas,10,true);
		// pick random fitness
		int fitnessRoll = RandomNumberManager.getRandomInt(0,Evaluation.fitnesses.length);
		// working:
		// Evolving for colorDifference ERROR
		// Evolving for shapeDifference
		// Evolving for distanceDifference
		// Evolving for groupingDifference ERROR
		//fitnessRoll=1;
		//fitnessRoll = 2;
		//System.out.println("Evolving for "+Evaluation.fitnesses[fitnessRoll]);
		ga.evolve(5, fitnessRoll, true);
		
		ArrayList<Canvas> result = ga.getBest(4);
		for(int i=0;i<result.size();i++){ result.get(i).ID = "Canvas "+i; }		// add IDs
		
		//System.out.println("Mad Scientist has made "+result.size()+" results!");
	
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
