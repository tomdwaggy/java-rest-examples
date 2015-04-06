/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.services.example;

import com.wordnik.swagger.annotations.ApiModel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brian Becker
 */
@XmlRootElement(name = "version")
@ApiModel("Version")
public class Version {
    
    @XmlElement(name = "v")
    private String v;
    
    public Version() {
        
    }
    
    public Version(String version) {
        this.v = version;
    }
    
}
