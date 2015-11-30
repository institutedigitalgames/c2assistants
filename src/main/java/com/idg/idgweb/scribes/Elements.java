/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author c2learn
 */
public class Elements {
    
    public enum Element {
        FIRE, WATER, WIND, EARTH, MYTH, FIFTH
    }
    
    public static String getElement(int elementTypeID){
        if(elementTypeID == 0){
            return "FIRE";
        } else if (elementTypeID == 1){
            return "WATER";
        } else if (elementTypeID == 2){
            return "WIND";
        } else if (elementTypeID == 3){
            return "EARTH";
        } else if (elementTypeID == 4){
            return "MYTH";
        } else if (elementTypeID == 5){
            return "FIFTH";
        } else {
            return "NOT DEFINED";
        }
    }
    
}
