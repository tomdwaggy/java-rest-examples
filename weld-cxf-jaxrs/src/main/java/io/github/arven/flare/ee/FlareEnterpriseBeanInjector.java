package io.github.arven.flare.ee;

import java.lang.reflect.Method;
import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.as.weld.WeldLogger;
import org.jboss.weld.injection.spi.EjbInjectionServices;
import org.jboss.weld.injection.spi.ResourceReference;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;
import org.jboss.weld.injection.spi.helpers.SimpleResourceReference;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.util.reflection.Reflections;

/**
 * Flare Embedded Framework EJB Support Class
 * 
 * @author Brian Becker
 */
public class FlareEnterpriseBeanInjector implements EjbInjectionServices {

    public ResourceReferenceFactory<Object> registerEjbInjectionPoint(InjectionPoint injectionPoint) {
        /*EJB ejb = injectionPoint.getAnnotated().getAnnotation(EJB.class);
        if (ejb == null) {
            throw WeldLogger.ROOT_LOGGER.annotationNotFound(EJB.class, injectionPoint.getMember());
        }
        if (injectionPoint.getMember() instanceof Method && ((Method) injectionPoint.getMember()).getParameterTypes().length != 1) {
            throw WeldLogger.ROOT_LOGGER.injectionPointNotAJavabean((Method) injectionPoint.getMember());
        }
        if (!ejb.lookup().equals("")) {
            return handleServiceLookup(ejb.lookup(), injectionPoint);
        } else {
            final ViewDescription viewDescription = getViewDescription(ejb, injectionPoint);
            if(viewDescription != null) {
                return handleServiceLookup(viewDescription, injectionPoint);
            } else {

                final String proposedName = ResourceInjectionUtilities.getEjbBindLocation(injectionPoint);
                return new ResourceReferenceFactory<Object>() {
                    @Override
                    public ResourceReference<Object> createResource() {
                        return new SimpleResourceReference<Object>(doLookup(proposedName, null));
                    }
                };
            }
        }*/
        throw new UnsupportedOperationException("No EJB injection supported");
    }

    public Object resolveEjb(InjectionPoint injectionPoint) {
        return registerEjbInjectionPoint(injectionPoint).createResource().getInstance();
    }

    public void cleanup() {
        // No cleanup required for embedded container
    }
    
}
