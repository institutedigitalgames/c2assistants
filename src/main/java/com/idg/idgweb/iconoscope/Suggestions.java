/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.iconoscope;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author c2learn
 */
@XmlRootElement(name = "suggestions")
public class Suggestions {
    
    @XmlElement(name = "suggestion")
    public ArrayList<Canvas> suggestions = null;
    
    public Suggestions() {
        this.suggestions = new ArrayList<Canvas>();
    }
    
    public Suggestions(ArrayList<Canvas> suggestions){
        this.suggestions = suggestions;
    }
    
    public void addCanvas(Canvas canvas){
//        if(suggestions == null)
//            this.suggestions = new ArrayList<Canvas>();

        this.suggestions.add(canvas);
    }
    
}
