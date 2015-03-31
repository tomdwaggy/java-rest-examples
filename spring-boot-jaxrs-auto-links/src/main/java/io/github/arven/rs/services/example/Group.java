package io.github.arven.rs.services.example;

import io.github.arven.rs.hypertext.HyperlinkId;
import io.github.arven.rs.hypertext.HyperlinkPath;
import io.github.arven.rs.hypertext.InjectHyperlinks;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The GroupData class is a simple class which provides a description to
 * a given group. This description does not contain a list of members,
 * just the group name and description.
 * 
 * @author Brian Becker
 */
@Entity
@Table(name="GROUPDATA")
@XmlRootElement(name = "group")
@XmlAccessorType(XmlAccessType.NONE)
@HyperlinkPath("/example/v1/group/{id}")
public class Group implements Serializable {

    @HyperlinkId @Id
    @XmlID @XmlAttribute
    private String id;
    
    @Basic
    @XmlElement
    private String description;
	
    @ManyToMany
    private List<Person> members;
    
    public Group() {
    	this.members = new LinkedList<Person>();
    }
    
    /**
     * Create a new GroupData with the given id and description.
     * 
     * @param   id              Name of the group which will be used in API
     * @param   description     Description of the group
     */
    public Group(String id, String description) {
    	super();
        this.id = id;
        this.description = description;
    }
    
    /**
     * Set the group id
     * 
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }    
    
    /**
     * Get the group id
     * 
     * @return	the group id
     */
    public String getId() {
    	return this.id;
    }
    
    /**
     * Get the list of members
     * @return 
     */
    public List<Person> getMembers() {
    	return this.members;
    }
    
    /**
     * Set the list of members
     * @param members
     */
    public void setMembers(List<Person> members) {
    	this.members = members;
    }    

    @Transient
    private List<Link> links = new LinkedList<Link>();

    @InjectHyperlinks
    @XmlElement(name = "link", namespace = "http://github.com/Arven/java-rest-examples/hypertext")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)       
    public Collection<Link> getLinks() {
        return links;
    }
    
}