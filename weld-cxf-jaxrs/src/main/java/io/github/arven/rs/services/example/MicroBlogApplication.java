package io.github.arven.rs.services.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.converter.ModelConverters;
import com.wordnik.swagger.jaxrs.listing.ApiListingResource;
import com.wordnik.swagger.jaxrs.listing.SwaggerSerializers;
import io.github.arven.rs.provider.JacksonJaxbYamlProvider;
import io.github.arven.rs.provider.Jsr250AuthorizationRequestFilter;
import javax.ws.rs.Path;

@ApplicationPath("/v1")
public class MicroBlogApplication extends Application {
    
    @Inject private MicroBlogRestResource restService;

    @Produces
    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(
            Arrays.asList(
                Jsr250AuthorizationRequestFilter.class,
                JacksonJaxbJsonProvider.class,
                JacksonJaxbYamlProvider.class,
                SwaggerSerializers.class,
                NextApiListingResource.class
            )
        );
    }
    
    @Produces 
    @Override
    public Set<Object> getSingletons() {
        return new HashSet<Object>(
            Arrays.asList(
                restService
            )
        );
    }    

    @Path("/resources")
    @Api("/resources")
    @Produces({"application/json","application/xml"})
    public static class NextApiListingResource extends ApiListingResource {
    }
    
}