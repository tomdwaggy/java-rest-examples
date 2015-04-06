package io.github.arven.rs.provider;

import com.google.common.base.Throwables;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * This is a simple exception mapper. It is designed to provide the entire
 * stack trace for development, so it returns it via the Guava Throwables
 * utility class, in a text/plain format. This should not be used in a
 * production system.
 *
 * @author Brian Becker
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
