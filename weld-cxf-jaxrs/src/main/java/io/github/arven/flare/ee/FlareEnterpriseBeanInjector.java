/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.ee;

import java.lang.reflect.Method;
import javax.ejb.EJB;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.injection.spi.EjbInjectionServices;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

/**
 * Flare Embedded Framework EJB Support Class
 * 
 * @author Brian Becker
 */
public class FlareEnterpriseBeanInjector implements EjbInjectionServices {

    public ResourceReferenceFactory<Object> registerEjbInjectionPoint(InjectionPoint injectionPoint) {
        EJB ejb = injectionPoint.getAnnotated().getAnnotation(EJB.class);
        if (ejb == null) {
            throw new IllegalArgumentException("Annotation not found");
        }
        if (injectionPoint.getMember() instanceof Method && ((Method) injectionPoint.getMember()).getParameterTypes().length != 1) {
            throw new IllegalArgumentException("Annotation not found");
        }
        if (!ejb.lookup().equals("")) {
            throw new UnsupportedOperationException("No support for remote beans in embedded server."); //To change body of generated methods, choose Tools | Templates.
        }
                
        return new FlareResourceReferenceFactory<Object>(injectionPoint.getType());
    }

    public Object resolveEjb(InjectionPoint injectionPoint) {
        return registerEjbInjectionPoint(injectionPoint).createResource().getInstance();
    }

    public void cleanup() {
        // No cleanup required for embedded container
    }
    
}
