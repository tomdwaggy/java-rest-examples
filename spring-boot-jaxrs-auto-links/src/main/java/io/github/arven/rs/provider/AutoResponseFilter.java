package io.github.arven.rs.provider;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import io.github.arven.rs.types.ListView;
import io.github.arven.rs.types.Hyper;
import io.github.arven.rs.types.Linked;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Link;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author brian.becker
 */
@Provider
public class AutoResponseFilter implements ContainerResponseFilter {

    public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {
        if(res.getEntity() instanceof StatusCode && res.getStatus() == 200) {
            StatusCode s = (StatusCode) res.getEntity();
            res.setStatus(s.error());
        }
        
        if(Linked.class.isInstance(res.getEntity())) {
            res.setEntity(
                new Hyper.Builder().entity(res.getEntity())
                        .link(Link.fromUri(req.getUriInfo().getRequestUri()).rel("self").build())
                        .build()
            );
        } else if(List.class.isInstance(res.getEntity())) {
            res.setEntity(
                new Hyper.Builder().entityList((List)res.getEntity())
                        .link(Link.fromUri(req.getUriInfo().getRequestUri()).rel("self").build())
                        .build()
            );
        } else if(ListView.class.isInstance(res.getEntity())) {
            ListView dl = (ListView)res.getEntity();
            res.setEntity(
                new Hyper.Builder().entityList(dl.collection()).limit(dl.limit()).offset(dl.offset()).reverse(dl.reverse())
                        .link(Link.fromUri(req.getUriInfo().getRequestUri()).rel("self").build())
                        .build()
            );
        }
    }
    
}