/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.services.example;

import io.github.arven.rs.filter.StatusCode;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Link.JaxbAdapter;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Brian Becker
 */
@XmlRootElement
public class StatusMessage implements StatusCode {
    
    @XmlElement
    private Response.Status status;
    
    @XmlElement
    @XmlJavaTypeAdapter(JaxbAdapter.class)
    private Link link;
    
    public StatusMessage(Response.Status status) {
        this.status = status;
        this.link = null;
    }    
    
    public StatusMessage(Response.Status status, Link link) {
        this.status = status;
        this.link = link;
    }
   
    public int error() {
        return status.getStatusCode();
    }
    
    public static StatusMessage created(Link l) {
        return new StatusMessage(Response.Status.CREATED, l);
    }
    
}
