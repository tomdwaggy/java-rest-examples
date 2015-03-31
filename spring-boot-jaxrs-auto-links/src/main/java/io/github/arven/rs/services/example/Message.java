package io.github.arven.rs.services.example;

import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import io.github.arven.rs.types.Linked;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The MessageData class provides some basic information about a message
 * posted by a user, or some other type of simple text message.
 * 
 * @author Brian Becker
 */
@Entity
@Table(name="MESSAGEDATA")
@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.NONE)
public class Message implements Serializable, Linked<Link> {
        
    @Id
    @XmlID @XmlAttribute
    private String id;
    
    @Basic
    @XmlAttribute
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    @Basic
    @XmlElement(name = "body")
    private String body;
    
    @Transient
    private List<String> tag;
    
    public Message () {
        this.date = (Calendar.getInstance().getTime());
    }
    
    /**
     * Create a new MessageData with the given ID and message. The data such
     * as the tags used will be automatically generated upon request. The
     * date will be set based on the current time when this object is created.
     * 
     * @param   id          The id or title of the message
     * @param   message     The message text, created as a value
     */
    public Message (String id, String message) {
        super();
        this.id = id;
        this.body = message;
    }
    
    /**
     * Set the message id
     * 
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Get the message id 
     * @return 
     */
    public String getId() {
        return this.id;
    }    
    
    /**
     * Get the list of tags from the message by parsing the string with a
     * regular expression. The tags are in the format #tagname throughout
     * the message, and they should all be picked up by the parser.
     * 
     * @return  List of tags in a message, generated on demand
     */
    @XmlElement
    public List<String> getTags() {
        if(body != null) {
            tag = new ArrayList<String>();
            Iterable<String> it = Iterables.filter(Splitter.on(" ").omitEmptyStrings().split(body), Predicates.containsPattern("^#.*$"));
            tag.addAll(Arrays.asList(Iterables.toArray(it, String.class)));
        }
        return tag;
    }

    @Transient
    private List<Link> links = new LinkedList<Link>();

    @Override
    @XmlElement(name = "link")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)       
    public Collection<Link> getLinks() {
        return links;
    }
    
}