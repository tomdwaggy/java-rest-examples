package io.github.arven.rs.hypertext;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * This is a web application failure or success response. This response
 * should be sent on the success of a POST, PUT, DELETE request in order
 * to give some feedback about what has just occurred, rather than sending
 * a void response.
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
    
    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    @XmlJavaTypeAdapter(type = Link.class, value = Link.JaxbAdapter.class)      
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
    
    private static void addLink(List links, Object id, String rel) {
        if(id.getClass().isAnnotationPresent(HyperlinkPath.class)) {
            HyperlinkPath path = id.getClass().getAnnotation(HyperlinkPath.class);
            links.add(Link.fromUri(UriBuilder.fromPath(path.value()).buildFromMap(HyperlinkUtils.getHyperlinkValues(id))).rel(rel).build());
        }
    }
    
    public WebStatusResponse(Response.Status status, Object... ids) {
        this(status);
        this.link = new LinkedList();
        String type = "self";
        for(Object id : ids) {
            addLink(this.link, id, type);
            type = "target";
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
