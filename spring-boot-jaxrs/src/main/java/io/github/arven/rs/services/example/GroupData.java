package io.github.arven.rs.services.example;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

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
public class GroupData implements Serializable {

    @Id
    @XmlID @XmlAttribute
    private String id;
    
    @Basic
    @XmlValue
    private String description;
	
    @ManyToMany
    private List<UserData> members;
    
    public GroupData() {
    	this.members = new LinkedList<UserData>();
    }
    
    /**
     * Create a new GroupData with the given id and description.
     * 
     * @param   id              Name of the group which will be used in API
     * @param   description     Description of the group
     */
    public GroupData(String id, String description) {
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
    public List<UserData> getMembers() {
    	return this.members;
    }
    
    /**
     * Set the list of members
     * @param members
     */
    public void setMembers(List<UserData> members) {
    	this.members = members;
    }    
    
}