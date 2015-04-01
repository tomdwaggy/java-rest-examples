/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.services.example;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brian Becker
 */
@XmlRootElement
public class Version {
    
    @XmlElement
    private String version;
    
    public Version() {
        
    }
    
    public Version(String version) {
        this.version = version;
    }
    
}
