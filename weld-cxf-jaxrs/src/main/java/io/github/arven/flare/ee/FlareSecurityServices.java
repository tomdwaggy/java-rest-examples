package io.github.arven.flare.ee;

import java.security.Principal;
import org.jboss.weld.security.spi.SecurityServices;

/**
 * @author Brian Becker
 */
public class FlareSecurityServices implements SecurityServices {

    public Principal getPrincipal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void cleanup() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
