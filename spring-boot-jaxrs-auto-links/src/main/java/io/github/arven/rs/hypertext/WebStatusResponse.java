/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.hypertext;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
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
public class WebStatusResponse implements WebStatusCode {
    
    @XmlElement(name="code")
    private int error;
    
    @XmlElement(name="message")
    private String message;
    
    @XmlElement(name = "link", namespace = "http://github.com/Arven/java-rest-examples/hypertext")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)           
    private List<Link> link;
    
    public WebStatusResponse() { 
        this.error = 200;
        this.message = "No further details";
        this.link = Arrays.asList();
    }
    
    public WebStatusResponse(Response.Status status) {
        this.error = status.getStatusCode();
        this.message = status.getReasonPhrase();
        this.link = Arrays.asList();
    }
    
    public WebStatusResponse(Response.Status status, HyperlinkIdentifier... ids) {
        this(status);
        this.link = new LinkedList();
        for(HyperlinkIdentifier id : ids) {
            if(id.getClass().isAnnotationPresent(HyperlinkPath.class)) {
                HyperlinkPath path = id.getClass().getAnnotation(HyperlinkPath.class);
                this.link.add(Link.fromPath(path.value()).rel("self").build(id.getLinkedId()));
            }
        }
    }
 
    @Override
    public int error() {
        return error;
    }
    
    public String message() {
        return this.message;
    }
    
}
