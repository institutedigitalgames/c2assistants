/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author c2learn
 */
@XmlRootElement
public class JSONTestObject {
    
    public String name;
    public int age;
    
    public JSONTestObject(){
        
    }
    
    public JSONTestObject(String name, int age){
        this.name = name;
        this.age = age;
    }
}
