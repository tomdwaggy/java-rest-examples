package io.github.arven.flare.ee;

import org.jboss.weld.ejb.api.SessionObjectReference;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.ejb.spi.EjbServices;
import org.jboss.weld.ejb.spi.InterceptorBindings;

/**
 * @author Brian Becker
 */
public class FlareEnterpriseBeanServices implements EjbServices {

    public SessionObjectReference resolveEjb(EjbDescriptor<?> ejbDescriptor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void registerInterceptors(EjbDescriptor<?> ejbDescriptor, InterceptorBindings interceptorBindings) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void cleanup() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
