package io.github.arven.flare.ee;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.weld.injection.spi.ResourceInjectionServices;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

/**
 * @author Brian Becker
 */
public class FlareResourceInjector implements ResourceInjectionServices {
    
    public ResourceReferenceFactory<Object> registerResourceInjectionPoint(InjectionPoint injectionPoint) {
        Resource res = injectionPoint.getAnnotated().getAnnotation(Resource.class);
        if(res.name().isEmpty()) {
            return registerResourceInjectionPoint(res.lookup(), null);
        } else if(!res.name().isEmpty()) {
            return registerResourceInjectionPoint(res.name(), null);
        }
        throw new RuntimeException("Not able to register resource for injection point " + injectionPoint.toString());
    }

    public ResourceReferenceFactory<Object> registerResourceInjectionPoint(String jndiName, String mappedName) {
        try {
            Context ctx = new InitialContext();
            Object obj;
            if(jndiName != null) {
                obj = ctx.lookup(jndiName);
                return new FlareResourceReferenceFactory<Object>(obj);
            }
        } catch (NamingException ex) {
            Logger.getLogger(FlareResourceInjector.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException("Not able to register resource for JNDI name " + jndiName);
    }

    public Object resolveResource(InjectionPoint injectionPoint) {
        return registerResourceInjectionPoint(injectionPoint).createResource().getInstance();
    }

    public Object resolveResource(String jndiName, String mappedName) {
        return registerResourceInjectionPoint(jndiName, mappedName).createResource().getInstance();
    }

    public void cleanup() {
        // No cleanup needed for embedded server
    }
    
}
