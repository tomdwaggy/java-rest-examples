package io.github.arven.flare.rs;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.util.AnnotationLiteral;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

@Provider
public class Jsr250AuthorizationRequestFilter implements ContainerRequestFilter {
    
    @Context ResourceInfo info;
    
    public static Annotation permissions(AnnotatedElement ae) {
        for(Annotation a : ae.getDeclaredAnnotations()) {
            if(a instanceof RolesAllowed || a instanceof PermitAll || a instanceof DenyAll) {
                return a;
            }
        }
        return new AnnotationLiteral<PermitAll>() {};
    }
    
    public static boolean allows(Annotation a, SecurityContext ctx) {
        if(a.annotationType().equals(PermitAll.class)) {
            return true;
        } else if(a.annotationType().equals(RolesAllowed.class)) {
            RolesAllowed ra = (RolesAllowed) a;
            for(String role : ra.value()) {
                if(ctx != null && ctx.isUserInRole(role)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException { 
        Annotation inner = permissions(info.getResourceMethod());
        Annotation outer = permissions(info.getResourceClass());
        if(allows(outer, ctx.getSecurityContext())) {
            if(allows(inner, ctx.getSecurityContext())) {
                return;
            }
        }
        throw new WebApplicationException(401);
    }
   
}
