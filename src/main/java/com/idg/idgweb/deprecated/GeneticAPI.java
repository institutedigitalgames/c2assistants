/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.deprecated;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.idg.idgweb.iconoscope.Canvas;

/**
 *
 * @author c2learn
 */
@Path("api")
public class GeneticAPI {
    
    private String _corsHeaders;

    private Response makeCORS(ResponseBuilder req, String returnMethod) {
       ResponseBuilder rb = req.header("Access-Control-Allow-Origin", "*")
          .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");

       if (!"".equals(returnMethod)) {
          rb.header("Access-Control-Allow-Headers", returnMethod);
       }

       return rb.build();
    }

    private Response makeCORS(ResponseBuilder req) {
       return makeCORS(req, _corsHeaders);
    }
    
    @GET
    @Produces("application/json")
    public Suggestions get() {
        //return Response.ok(createSuggestions(), MediaType.APPLICATION_JSON).build();
        return createSuggestions();
        
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String postData(){
        return "Hello World!";
    }
    
//    @OPTIONS
//    public Response corsMyResource(@HeaderParam("Access-Control-Request-Headers") String requestH) {
//      _corsHeaders = requestH;
//      return makeCORS(Response.ok(), requestH);
//   }
//    
    
    public static Suggestions createSuggestions(){
        Suggestions suggestions = new Suggestions();
        return suggestions;
    }
}
