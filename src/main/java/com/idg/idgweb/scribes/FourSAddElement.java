/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes;

import com.owlike.genson.annotation.JsonProperty;
import java.util.ArrayList;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author c2learn
 */
@Path("4scribes/addelement")
@Produces("application/json")
public class FourSAddElement {
    
    public FourSAddElement(){
        
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String addElements(String str){
        JSONArray json = new JSONArray(str);
        
        ArrayList<JSONObject> json_list = new ArrayList<>();
        for(int i = 0; i < json.length(); i++){
            json_list.add(json.getJSONObject(i));
        }
        /*
        for(int i = 0; i < json_list.size(); i++){
			System.out.println(json_list.get(i).get("CardID").toString());
            System.out.println(json_list.get(i).get("Name").toString());
            System.out.println(json_list.get(i).get("IsCharacter").toString());
        }
        */
		return "Successfull addition";
    }
    
}
