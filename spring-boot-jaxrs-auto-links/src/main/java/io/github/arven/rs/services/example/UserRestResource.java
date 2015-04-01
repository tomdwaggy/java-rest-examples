/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.services.example;

import io.github.arven.rs.hypertext.Hyperlinked;
import io.github.arven.rs.hypertext.WebStatusResponse;
import static io.github.arven.rs.services.example.MicroBlogRestResource.MAX_LIST_SPAN;

import io.github.arven.rs.hypertext.ListView;
import java.io.Serializable;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

/**
 * This is the User representation for the RESTful web service. You are able
 * to get user details, remove your own user account, get the friends list
 * for a user, as well as alter the friends list of your own user account.
 * 
 * @author Brian Becker
 */
@Named
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class UserRestResource implements Serializable {
        
    @Inject private MicroBlogService blogService;
    
    /**
     * This method gets a user and displays it as one of the primary content
     * types.
     * 
     * @param name
     * @return 
     */
    @GET @Hyperlinked
    public Person getUser(@PathParam("name") String name) {
        return blogService.getUser(name);
    }
    
    /**
     * This method deletes a user and returns nothing to the user which calls
     * this function. Only the user himself can delete the user, there is no
     * concept of administrator users in this demo.
     * 
     * @param name
     * @param ctx 
     * @return  
     */
    @DELETE @Hyperlinked
    @RolesAllowed({"User"})
    public WebStatusResponse removeUser(@PathParam("name") String name, final @Context SecurityContext ctx) {
        if(ctx.getUserPrincipal().getName().equals(name)) {
            blogService.removeUser(name);
            return new WebStatusResponse(Status.OK);
        } else {
            return new WebStatusResponse(Status.FORBIDDEN);
        }
    }
    
    /**
     * This method gets a list of friends for a given user, all users are
     * allowed to look at this information.
     * 
     * @param name
     * @param offset
     * @return 
     */
    @Path("/friends") @GET @Hyperlinked
    public ListView<Person> getFriendsList(@PathParam("name") String name, @MatrixParam("offset") Integer offset) {
        return new ListView<Person>(blogService.getFriends(name)).offset(offset).limit(MAX_LIST_SPAN);
    }
    
    /**
     * For a given user, this method adds a friend. Only the user which is
     * authenticated can call this method for themselves, but it can be
     * called with a parameter representing any person in the database
     * as long as they exist.
     * 
     * There is no transacted friends list support which requires a request
     * to a user in this demo.
     * 
     * @param name
     * @param friend
     * @param ctx 
     * @return  
     */
    @Path("/friends/{friend}") @PUT @Hyperlinked
    @RolesAllowed({"User"})
    public WebStatusResponse addFriend(@PathParam("name") String name, @PathParam("friend") String friend, final @Context SecurityContext ctx) {
        if(ctx.getUserPrincipal().getName().equals(name)) {
            blogService.addFriend(name, friend);
            return new WebStatusResponse(Status.CREATED, blogService.getUser(name), blogService.getUser(friend));
        } else {
            return new WebStatusResponse(Status.FORBIDDEN);
        }
    }
    
    /**
     * For a given user, this method removes a friend. Only the user which
     * is authenticated can call this method for themselves.
     * 
     * The friends list is not mutual, therefore it has no effect on the
     * other user in this demo.
     * 
     * @param name
     * @param friend
     * @param ctx 
     * @return  
     */
    @Path("/friends/{friend}") @DELETE @Hyperlinked
    @RolesAllowed({"User"})
    public WebStatusResponse removeFriend(@PathParam("name") String name, @PathParam("friend") String friend, final @Context SecurityContext ctx) {
        if(ctx.getUserPrincipal().getName().equals(name)) {
            blogService.removeFriend(name, friend);
            return new WebStatusResponse(Status.OK);
        } else {
            return new WebStatusResponse(Status.FORBIDDEN);
        }
    }
    
    /**
     * For a given user, this method gets all the messages posted in their
     * name. Any user can call this method with any username as a parameter.
     * The parameters allow setting the offset where the list is shown, as
     * the entire list will not be returned if it is too large.
     * 
     * @param name
     * @param offset
     * @return 
     */
    @Path("/messages") @GET @Hyperlinked
    public ListView<Message> getMessagesByUser(@PathParam("name") String name, @MatrixParam("offset") Integer offset) {
        return new ListView(blogService.getPosts(name)).offset(offset).reverse(true);
    }
    
    /**
     * For a given user, this method posts a message in their name. Any
     * user can call this method with any parameters, the name is recalled
     * directly from the security context. If the name does not match,
     * then nothing will happen.
     * 
     * @param name
     * @param post
     * @param ctx 
     * @return  
     */
    @Path("/messages") @POST @Hyperlinked
    @RolesAllowed({"User"})
    public WebStatusResponse postMessage(@PathParam("name") String name, Message post, final @Context SecurityContext ctx) {
        if(ctx.getUserPrincipal().getName().equals(name)) {
            blogService.addPost(ctx.getUserPrincipal().getName(), post);
            return new WebStatusResponse(Status.CREATED, post);
        } else {
            return new WebStatusResponse(Status.FORBIDDEN);
        }
    }
    
    /**
     * For a given user name, this method gets a specific message, if it
     * exists.
     * 
     * @param name
     * @param message
     * @return  
     */
    @Path("/messages/{message}") @GET @Hyperlinked
    public List<Message> getSingleMessage(@PathParam("name") String name, @PathParam("message") String message) {
        return blogService.getPost(name, message);
    }    
    
}
