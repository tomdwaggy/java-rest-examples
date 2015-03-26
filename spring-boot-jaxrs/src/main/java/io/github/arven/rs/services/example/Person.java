package io.github.arven.rs.services.example;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The UserData class provides all of the general information about a user,
 * aside from relational data. This consists of a user id, a nickname, an
 * email address, as well as a password.
 * 
 * @author Brian Becker
 */
@Entity
@Table(name="USERDATA")
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.NONE)
public class Person implements Serializable {

    @Id
    @XmlID @XmlAttribute
    private String id;
    
    @Basic
    @XmlElement
    private String nickname;
    
    @Basic
    @XmlElement
    private String email;
    
    @Basic
    @XmlElement
    private String password;
	
    @OneToMany(cascade = CascadeType.ALL) @JoinColumn(name="USERDATA_ID")
    private List<Message> messages;
	
    @ManyToMany(mappedBy = "members")
    private List<Group> groups;
	
    @OneToMany(cascade = CascadeType.ALL) @JoinTable(name="HAS_FRIEND")
    private List<Person> friends;
    
    public Person() {
    	this.messages = new LinkedList<Message>();
    	this.groups = new LinkedList<Group>();
    	this.friends = new LinkedList<Person>();
    }
    
    /**
     * Create a new UserData with the user id, nickname, email, and password
     * for the user.
     * 
     * NOTE: The password is stored internally in a Password object, which
     * prevents the marshaling of the actual password to client requests,
     * regardless of permissions. Either no value at all, or a placeholder
     * value may be returned.
     * 
     * @param   id          User id for the user
     * @param   nickname    Nickname for the user
     * @param   email       Email address for the user
     * @param   password    Password for the user (used for authentication)
     */
    public Person(String id, String nickname, String email, String password) {
    	super();
        this.id       = id;
        this.nickname = nickname;
        this.email    = email;
        this.password = password;
    }
    
    /**
     * Set the user id
     * 
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Get the user id
     * 
     * @return	the user's id
     */
    public String getId() {
    	return this.id;
    }
    
    /**
     * Get the user id
     * 
     * @return	the user's id
     */
    public String getNickname() {
    	return this.nickname;
    }    
    
    /**
     * Get the user password
     * 
     * @return the user's password
     */
    public String getPassword() {
    	return this.password;
    }
    
    /**
     * Get the list of messages for this user
     * @return 
     */
    public List<Message> getMessages() {
    	return this.messages;
    }
    
    /**
     * Get the groups this user is in
     * @return 
     */
    public List<Group> getGroups() {
    	return this.groups;
    }
    
    /**
     * Set the groups this user is in
     * @param groups
     */
    public void setGroups(List<Group> groups) {
    	this.groups = groups;
    }
    
    /**
     * Get the friends this user has
     * @return 
     */
    public List<Person> getFriends() {
    	return this.friends;
    }
    
    /**
     * Set the friends this user has
     * @param friends
     */
    public void setFriends(List<Person> friends) {
    	this.friends = friends;
    }
    
    /**
     * Set the email address of this user
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Get the email address of this user
     */
    public String getEmail() {
        return this.email;
    }
    
}
