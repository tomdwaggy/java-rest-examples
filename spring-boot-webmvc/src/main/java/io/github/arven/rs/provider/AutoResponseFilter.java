package io.github.arven.rs.provider;

import io.github.arven.rs.hypertext.WebStatusCode;
import io.github.arven.rs.hypertext.ListView;
import io.github.arven.rs.hypertext.Hyper;
import io.github.arven.rs.hypertext.HyperlinkPath;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Link;
import javax.ws.rs.ext.Provider;

/**
 * This filter wraps various types of responses. It handles the WebStatusCode
 * types of responses, setting the actual HTTP status code before returning.
 * It also handles the HyperlinkIdentifier classes, as well as lists of the
 * same, of which it builds the Hyper wrapper object and sets the new return
 * value. It also sets the initial link of the request, by providing a link
 * on the Hyper wrapper. This initial link should be used for same-request
 * level interactions, not objects returned in the list.
 * 
 * @author Brian Becker
 */
@Provider
public class AutoResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {
        if(res.getEntity() instanceof WebStatusCode && res.getStatus() == 200) {
            WebStatusCode s = (WebStatusCode) res.getEntity();
            res.setStatus(s.error());
        }
        
        // Start Building Hyper Wrapper
        Hyper.Builder builder = new Hyper.Builder()
                .self(Link.fromUri(URI.create("/").resolve(req.getUriInfo().getPath())).rel("self").type("application/xml").build())
                .type("application/xml");
                
        // Get Matched Classes
        List<Object> matched =  req.getUriInfo().getMatchedResources();
        if(matched.size() > 0) {
            builder.matcher(matched.get(0).getClass());
        }
        
        // Get Matched Paths
        if(req.getUriInfo().getMatchedURIs().size() > req.getUriInfo().getMatchedResources().size()) {
            List<String> matchedMethods = req.getUriInfo().getMatchedURIs();
            builder.method(URI.create("/").resolve(URI.create(matchedMethods.get(1)).relativize(URI.create(matchedMethods.get(0)))));
        }
        
        if(res.getEntity().getClass().isAnnotationPresent(HyperlinkPath.class)) {
            res.setEntity(builder.entity(res.getEntity()).build());
        } else if(List.class.isInstance(res.getEntity())) {
            res.setEntity(builder.entityList((List)res.getEntity()).build());
        } else if(ListView.class.isInstance(res.getEntity())) {
            res.setEntity(builder.entityList(((ListView)res.getEntity()).collection()).build());
        }
        
    }
    
}
