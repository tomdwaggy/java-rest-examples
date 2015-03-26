package io.github.arven.rs.provider;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author brian.becker
 */
@Provider
public class StatusCodeFilter implements ContainerResponseFilter {

    public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {
        if(res.getEntity() instanceof StatusCode && res.getStatus() == 200) {
            StatusCode s = (StatusCode) res.getEntity();
            res.setStatus(s.error());
        }
    }
    
}
