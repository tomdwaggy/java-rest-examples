/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.server.jetty;

import javax.enterprise.inject.Produces;
import org.eclipse.jetty.security.HashLoginService;

/**
 *
 * @author Brian Becker
 */
public class FlareLoginService {
    
    private static HashLoginService hash;

    @Produces @JettyService
    public HashLoginService getLoginService() {
        if(hash == null) { 
            hash = new HashLoginService();
        }
        return hash;
    }
    
}
