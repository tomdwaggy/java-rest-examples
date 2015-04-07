package io.github.arven.rs.services.example;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import io.github.arven.rs.provider.Secure;
import javax.annotation.security.RolesAllowed;

import javax.inject.Inject;
import javax.inject.Named;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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
@Path("/")
@Api(value = "/example/v1", description = "Microblog Service", tags = {"example"})
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "application/yaml" })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "application/yaml" })
public class MicroBlogRestResource {
    
    public static int MAX_LIST_SPAN = 10;
    
    @Inject private MicroBlogService blogService;
    @Inject private UserRestResource userResource;
    @Inject private GroupRestResource groupResource;
    
    /**
     * The most trivial example of a REST method, simply get the version and
     * return it as a raw string with a MIME type of text/plain.
     * 
     * @return  Version of this demo
     */
    @ApiOperation("Get version of this web service")
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
    @ApiOperation("Create a new user group")
    @Path("/group") @POST @RolesAllowed({"User"})
    public void createGroup(@ApiParam Group group, final @Context SecurityContext ctx) {
        blogService.addGroup(group, ctx.getUserPrincipal().getName());
    }        
    
    /**
     * Get some operations on a specific group.
     * 
     * @return sub resource for group
     */
    @ApiOperation(value = "Get operations on a selected group", response = GroupRestResource.class)
    @Path("/group/{group}")
    public GroupRestResource getGroupSubResource() {
        return groupResource;
    }
    
    /**
     * This method adds a user via the injected MicroBlogService backend,
     * which adds the user and credential to the LDAP server as well as adds
     * the user itself to the database.
     * 
     * @param user
     * @return 
     */
    @ApiOperation(value = "Add a user")
    @Path("/user") @POST
    public void addUser(@ApiParam Person user) {
        blogService.addUser(user);
    }    
    
    /**
     * Get some operations on a specific user.
     * 
     * @return sub resource for user
     */
    @ApiOperation(value = "Get operations on a selected user", response = UserRestResource.class)
    @Path("/user/{name}")
    public UserRestResource getUserSubResource() {
        return userResource;
    }
 
}