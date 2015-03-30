/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.provider;

import com.google.common.base.Throwables;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author brian.becker
 */
@Provider
public class AnyExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException arg0) {
        if(arg0 instanceof WebApplicationException) {
            WebApplicationException wae = (WebApplicationException) arg0;
            return Response.status(wae.getResponse().getStatus()).entity(Throwables.getStackTraceAsString(arg0)).type("text/plain").build();
        } else {
            return Response.status(500).entity(Throwables.getStackTraceAsString(arg0)).type("text/plain").build();
        }
    }

}
