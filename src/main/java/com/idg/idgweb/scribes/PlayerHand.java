/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes;

import com.idg.idgweb.scribes.Assistants.Assistant;
import com.idg.idgweb.scribes.ElementCard;
import java.util.ArrayList;
import java.util.Random;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author c2learn
 */

@XmlRootElement(name = "playerhand")
@XmlAccessorType(XmlAccessType.FIELD)
public final class PlayerHand {
    public static final int charsPerHand = 1;
    
    public ArrayList<ElementCard> player_hand = null;
    
    //@XmlElement(required = false)
    //private ArrayList<ElementCard> cards_available = (ArrayList<ElementCard>) ElementList.getElementList().clone();
    
    //@XmlElement(required = false)
    //private ArrayList<ElementCard> myths_available = (ArrayList<ElementCard>) ElementList.getMythList().clone();
    
    @XmlElement(required = false)
    public Random random = new Random();
    
    @XmlElement(required = false)
    public static final int DEFAULT_HAND_SIZE = 6;
    
    public PlayerHand() {
        //this.player_hand = new ArrayList<>();
    }
    
    public PlayerHand(Assistant assistant){
        //if(assistant == Assistants.Assistant.CHAOTIC)
            chaoticKate();
        //else 
            //this.player_hand = new ArrayList<>();
    }
    
    
    public void chaoticKate(){
        player_hand = new ArrayList<>();
        
        // Add Myth Card to Hand
        player_hand.add(ElementList.getInstance().getElementList().get(random.nextInt(ElementList.getInstance().getElementList().size())));
        while(player_hand.size() < DEFAULT_HAND_SIZE){
            player_hand.add(ElementList.getInstance().getElementList().get(random.nextInt(ElementList.getInstance().getElementList().size())));
        }
    }
    
//    public void resetAvailableElements(){
//        cards_available = (ArrayList<ElementCard>) ElementList.getElementList().clone();
//        myths_available = (ArrayList<ElementCard>) ElementList.getMythList().clone();
//    }
}
