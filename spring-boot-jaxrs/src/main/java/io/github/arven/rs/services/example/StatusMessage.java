/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.services.example;

import io.github.arven.rs.provider.StatusCode;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Link.JaxbAdapter;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Brian Becker
 */
@XmlRootElement(name = "status")
@XmlAccessorType(XmlAccessType.NONE)
public class StatusMessage implements StatusCode {
    
    @XmlElement(name="code")
    private int error;
    
    @XmlElement(name="message")
    private String message;
    
    public StatusMessage() { this.error = 200; }
    
    public StatusMessage(Response.Status status) {
        this.error = status.getStatusCode();
    }    
 
    public int error() {
        return error;
    }
    
    public String message() {
        return this.message;
    }
   
}
