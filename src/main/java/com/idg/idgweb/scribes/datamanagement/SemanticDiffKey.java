/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes.datamanagement;

/**
 *
 * @author c2learn
 */
public class SemanticDiffKey {
    
    public int ElementID_1;
    public int ElementID_2;
    
    
    public SemanticDiffKey(int ElementID_1, int ElementID_2){
        this.ElementID_1 = ElementID_1;
        this.ElementID_2 = ElementID_2;
    }
    
    @Override
    public int hashCode() {
        return (""+ElementID_1+ElementID_2).hashCode();
    }
    
    public int getFirstID(){
        return ElementID_1;
    }
    
    public int getSecondID(){
        return ElementID_2;
    }
    
    @Override
    public boolean equals(Object other){
        if( !(other instanceof SemanticDiffKey) )
            return false;
        else{
            SemanticDiffKey comp = (SemanticDiffKey)other;
            return (comp.ElementID_1 == this.ElementID_1 && comp.ElementID_2 == this.ElementID_2);  
        }
    }
    
    
}
