/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes;

import com.idg.idgweb.scribes.Elements.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author c2learn
 */
@XmlRootElement(name = "Element")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElementCard {
    
    private static int ID_Count = 0;
    
    @XmlElement(name = "Name")
    private String name;
    
    @XmlElement(name = "ElementName")
    private String element_name;
    
    @XmlElement(name = "IsCharacter")
    private boolean is_character;
    
    @XmlElement(name = "CardNumber")
    private int card_number;
    
    @XmlElement(name = "CardID")
    private int ID;
    
    @XmlElement(name = "SemanticDiffValue")
    private double semanticDiffValue;
    
    @XmlElement(name = "NumberTimesPlayed")
    private int numberTimePlayed;
    
    public ElementCard() {
        
    }
    
    public ElementCard(String name, String elememt_name, boolean is_character, int card_number, int ID){
        this.name = name;
        this.element_name = elememt_name;
        this.is_character = is_character;
        this.card_number = card_number;
        this.ID = ID;
        this.semanticDiffValue = 0;
        this.numberTimePlayed = 1;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getElement(){
        return element_name;
    }
    
    public boolean isCharacter(){
        return is_character;
    }
    
    public int getCardNumber(){
        return card_number;
    }
    
    public int getID(){
        return this.ID;
    }
    
    public double getSemanticDifferenceValue(){
        return this.semanticDiffValue;
    }
    
    public void setSemanticDifferenceValue(double newDiffValue){
        this.semanticDiffValue = newDiffValue;
    }
    
    public int getTimesPlayed(){
        return this.numberTimePlayed;
    }
    
    public void incPlayed(){
        this.numberTimePlayed++;
    }
    
    public void resetPlayed(){
        this.numberTimePlayed = 0;
    }
    
    public static ElementCard createElementCard(String name, String element_name, int card_number){
        ID_Count++;
        return new ElementCard(name, element_name, false, card_number, ID_Count);
    }
    
    public static ElementCard createElementCharacter(String name, String element_name, int card_number){
        ID_Count++;
        return new ElementCard(name, element_name, true, card_number, ID_Count);
    }
    
    public static ElementCard createCustomElement(int ID, String name, String element_name, boolean isCharacter, int card_number){
        return new ElementCard(name, element_name, isCharacter, card_number, ID);
    }
}
