package io.github.arven.rs.services.example;

import io.github.arven.rs.types.Hyper;
import javax.annotation.security.RolesAllowed;

import javax.inject.Inject;
import javax.inject.Named;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

/**
 * MicroBlogRestService is the REST service front end for the MicroBlogService.
 * Most of the calls are delegated directly to the MicroBlogService. There are
 * a number of annotations on each of the paths, which specify the majority of
 * the REST parameters.
 * 
 * @author Brian Becker
 */

@Named
@Path("/v1")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "text/html" })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class MicroBlogRestResource {
    
    public static int MAX_LIST_SPAN = 10;
    
    private final MicroBlogService blogService;
    
    @Inject
    public MicroBlogRestResource(MicroBlogService blogService) {
        this.blogService = blogService;
    }
    
    /**
     * The most trivial example of a REST method, simply get the version and
     * return it as a raw string with a MIME type of text/plain.
     * 
     * @return  Version of this demo
     */
    @Path("/version") @GET
    public Version getVersion() {
        return new Version("v1.1");
    }
    
    /**
     * For a given user, this method creates a group and inserts the user into
     * the group. Any user can call this method and any group name can be used
     * as long as the group name is available.
     * 
     * @param group
     * @param ctx 
     * @return  
     */
    @Path("/group") @POST @RolesAllowed({"User"})
    public Hyper<StatusMessage> createGroup(Group group, final @Context SecurityContext ctx) {
        try {
            blogService.addGroup(group, ctx.getUserPrincipal().getName());
            return new Hyper.Builder(new StatusMessage(Status.CREATED))
                    .link(Link.fromPath("/example/v1/group/{name}").rel("created").build(group.getId()))
                    .build();
        } catch (Exception e) {
            return new Hyper.Builder(new StatusMessage(Status.CONFLICT)).build();
        }
    }        
    
    /**
     * Get some operations on a specific group.
     * 
     * @return sub resource for group
     */
    @Path("/group/{group}")
    public GroupRestResource getGroupSubResource() {
        return new GroupRestResource(blogService);
    }
    
    /**
     * This method adds a user via the injected MicroBlogService backend,
     * which adds the user and credential to the LDAP server as well as adds
     * the user itself to the database.
     * 
     * @param user
     * @return 
     */
    @Path("/user") @POST
    public Hyper<StatusMessage> addUser(Person user) {
        try {
            blogService.addUser(user);
            //return StatusMessage.created(Link.fromPath("/example/v1/user/{name}").rel("created").build(user.getId()));
            return new Hyper.Builder(new StatusMessage(Status.CREATED))
                    .link(Link.fromPath("/example/v1/user/{name}").rel("created").build(user.getId()))
                    .build();                    
        } catch (Exception e) {
            return new Hyper.Builder(new StatusMessage(Status.CONFLICT)).build();
        }
    }    
    
    /**
     * Get some operations on a specific user.
     * 
     * @param name
     * @return sub resource for user
     */
    @Path("/user/{name}")
    public UserRestResource getUserSubResource(@PathParam("name") String name) {
        return new UserRestResource(blogService);
    }
 
}