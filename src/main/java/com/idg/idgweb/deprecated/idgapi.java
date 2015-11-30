/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.deprecated;

import javax.json.Json;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author c2learn
 */
@Path("suggestions")
//@Produces(MediaType.APPLICATION_JSON)
public class idgapi {
    
    /**
     * Retrieves representation of an instance of com.idg.idgweb.idgapi
     * @return an instance of javax.json.Json
     */
    @GET
    @Produces("application/json")
    public Suggestions get() {
        return GeneticAPI.createSuggestions();
    }

    /**
     * PUT method for updating or creating an instance of idgapi
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(Json content) {
    }
}
