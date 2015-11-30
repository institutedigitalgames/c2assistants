/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes;

import com.idg.idgweb.scribes.Assistants.Assistant;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author c2learn
 */
@XmlRootElement(name = "gamehand")
@XmlAccessorType(XmlAccessType.FIELD)
public class GameHands {
    
    @XmlElement(name = "playerhand")
    public ArrayList<PlayerHand> gamehand = null;
    
    public GameHands() {
        
    }
    
    public GameHands(int number_of_players, Assistant c2assistant){
        gamehand = new ArrayList<>();
        
        for(int i = 0; i < number_of_players; i++){
            gamehand.add(new PlayerHand(c2assistant));
        }
    }
    
}
