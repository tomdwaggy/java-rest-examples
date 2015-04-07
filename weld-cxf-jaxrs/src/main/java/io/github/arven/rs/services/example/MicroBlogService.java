package io.github.arven.rs.services.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.util.security.Credential;

/**
 * MicroBlogService is a backend implementation, with no database or any
 * persistent store, which can be used to provide services via the class
 * MicroBlogRestService. This service provides just enough functionality
 * to demonstrate and test the web service layer itself.
 * 
 * @author Brian Becker
 */
@Stateless @MockDatabase
public class MicroBlogService {
	
    @PersistenceContext
    private EntityManager test;
    
    @Inject
    private HashLoginService loginService;
 
    /**
     * Get the user data for a given user.
     * 
     * @param   userName        user id for the user
     * @return  The data for the user
     */
    public Person getUser( String userName ) {
    	return test.find(Person.class, userName);
    }
    
    /**
     * Create a new user by providing a UserData object for configuration. It
     * must contain a user id, in order to refer to the user.
     * 
     * @param   user        user data for the user, containing user id
     */
    public void addUser( Person user ) {
        loginService.putUser(user.getId(), Credential.getCredential(user.getPassword()), new String[] { "User" });
        test.persist(user);
    }
    
    /**
     * Delete a given user with a given user id, which will be used to remove
     * the user from the database.
     * 
     * @param   userName        user data for the user, containing user id
     */
    public void removeUser( String userName ) {
        Person user = test.find(Person.class, userName);
        user.getGroups().clear();
        user.getMessages().clear();
        test.persist(user);
        test.remove(user);
    }    
    
    /**
     * Get a list of posts from the given user. If the user does not exist,
     * then we will get back an empty list containing no posts. If the user
     * has not posted anything, we will also get back an empty list of no
     * posts.
     * 
     * @param   userName        user id for the user whose posts we want
     * @return  a list of posts from the user
     */
    public List<Message> getPosts( String userName ) {
        Person d = test.find(Person.class, userName);
        if (d != null) {
            return new LinkedList<Message>(d.getMessages());
        }
        return new LinkedList<Message>();
    }
    
    /**
     * Get a specific post, if it is owned by the user
     * 
     * @param   userName        user id for the user whose posts we want
     * @param   postName
     * @return  a single matching post
     */
    public List<Message> getPost( String userName, String postName ) {
        Message m = test.find(Message.class, postName);
        if (m != null && m.getUserName().equals(userName)) {
            return Arrays.asList(m);
        } else {
            return Collections.EMPTY_LIST;
        }
    }    

    /**
     * Add a post for the given user. If the user does not exist, the post
     * will still be added under the chosen user id. Therefore, it is
     * necessary to check whether the user exists in the user list before
     * posting a message.
     * 
     * @param   userName        user id for the user who will be posting
     * @param   post        message which should be posted by the user
     */
    public void addPost( String userName, Message post ) {
        Person user = test.find(Person.class, userName);
        user.getMessages().add(post);
        test.persist(post);
    }
    
    /**
     * Delete a give post.
     * 
     * @param   userName        user id for the user who will be posting
     * @param postName
     */
    public void removePost( String userName, String postName ) {
        Message message = test.find(Message.class, postName);
        if(message.getUserName().equals(userName)) {
            test.remove(message);
        }
    }
    
    /**
     * Get the group information for a given group. If the group does not
     * exist then we will get a null value.
     * 
     * @param   groupName       group id for the group we want information about
     * @return  The group information
     */
    public Group getGroup( String groupName ) {
        return test.find(Group.class, groupName);
    }
    
    /**
     * Get a list of group members for a given group. If the group does not
     * exist then we will get an empty list.
     * 
     * @param   groupName       group id for the group we want members of
     * @return  The group member list
     */
    public List<Person> getGroupMembers( String groupName ) {
        Group d = test.find(Group.class, groupName);
        if (d != null) {
            return new LinkedList<Person>(d.getMembers());
        }
        return new LinkedList<Person>();
    }
    
    /**
     * Add a group which does not exist. If the group does already exist,
     * then we will return an HTTP 409 Conflict exception. The user who
     * creates the group will be put in the group, but the group is not
     * contingent on the user who created the group staying in the group.
     * 
     * @param   group       group data for the group we want to create
     * @param   userName    username of the person who is creating the group
     */
    public void addGroup( Group group, String userName ) {
    	if(!test.contains(group)) {
            Person user = test.find(Person.class, userName);
            group.getMembers().add(user);    		
            test.persist(group);
    	}
    }
    
    /**
     * Add a group member to a group which does exist. The group must exist,
     * or the method will do nothing, but the username is expected to exist.
     * 
     * @param   groupName       group data for the group we want to join
     * @param   userName    username who is joining the group
     */
    public void addGroupMember( String groupName, String userName ) {
        Group group = test.find(Group.class, groupName);
        Person user = test.find(Person.class, userName);
        if(!group.getMembers().contains(user)) {
            group.getMembers().add(user);
            test.persist(group);
        }
    }
    
    /**
     * Leave a group which a given user is a member of. The group need not
     * exist, if it does the method will simply do nothing. If the group
     * exists, and this user is the last user in a given group, then we will
     * simply remove the entire group data. This allows groups to be
     * reclaimed.
     * 
     * @param   groupName       group id which user is trying to leave
     * @param   userName    user id which is leaving the group
     */
    public void leaveGroup( String groupName, String userName ) {
        Group group = test.find(Group.class, groupName);
        Person user = test.find(Person.class, userName);
        group.getMembers().remove(user);
        test.persist(group);
        if(group.getMembers().isEmpty()) {
        	removeGroup(groupName);
        }
    }
    
    /**
     * Remove a group without any questions asked. If there are members in
     * the group then the entire group becomes empty and it is reclaimed
     * for creation.
     * 
     * @param   groupName       group id for removal
     */
    public void removeGroup( String groupName ) {
        test.remove(test.find(Group.class, groupName));
    }
    
    /**
     * Get the friend list for a given user. If the user has not added any
     * friends yet, or the user does not exist, then the friends list will
     * be empty.
     * 
     * @param   userName    user id for friends list
     * @return  the friends list, or empty if not valid
     */
    public List<Person> getFriends( String userName ) {
        Person d = test.find(Person.class, userName);
        if (d != null) {
            return new LinkedList<Person>(d.getFriends());
        }
        return new LinkedList<Person>();
    }
    
    /**
     * Add a friend to the friend list for a given user. If the user has
     * added the friend already, it will be removed and replaced at the
     * bottom of the list. If the friend user id does not exist, then no
     * friend will be added.
     * 
     * @param   userName    user id which is adding a friend
     * @param   friendName  user id which is being added as a friend
     */
    public void addFriend( String userName, String friendName ) {
    	Person user = test.find(Person.class, userName);
    	Person friend = test.find(Person.class, friendName);
    	user.getFriends().add(friend);
    	test.persist(user);
    }
    
    /**
     * Remove a friend from a given user's friend list. If the user has
     * not added the user to their friend list, then the remove operation
     * will remove nothing.
     * 
     * @param   userName    user id which is removing a friend
     * @param   friendName  user id which is being removed as a friend
     */
    public void removeFriend( String userName, String friendName ) {
    	Person user = test.find(Person.class, userName);
    	Person friend = test.find(Person.class, friendName);
    	user.getFriends().remove(friend);
    	test.persist(user);
    }
    
}
