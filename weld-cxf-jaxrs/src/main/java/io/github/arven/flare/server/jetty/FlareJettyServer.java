/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.server.jetty;

import io.github.arven.flare.rs.ApiOriginFilter;
import io.github.arven.flare.server.FlareServer;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.DispatcherType;
import org.apache.cxf.cdi.CXFCdiServlet;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.jboss.weld.SimpleCDI;

/**
 * @author Brian Becker
 */
public class FlareJettyServer implements FlareServer {
    
    private final String webappDir;
    private Server server;
    
    public FlareJettyServer(String webappDir) {
        this.webappDir = webappDir;
    }
    
    public void init() {
        HashLoginService login = SimpleCDI.current().select(HashLoginService.class).get();
        server = new Server(8080);
        WebAppContext ctx = new WebAppContext();
        ctx.setContextPath("/*");
        ctx.addServlet(new ServletHolder(new CXFCdiServlet()), "/example/*");
        ctx.addFilter(ApiOriginFilter.class, "/example/*", EnumSet.of(DispatcherType.REQUEST));
        ctx.getSecurityHandler().setLoginService(login);
        ctx.getSecurityHandler().setAuthenticator(new BasicAuthenticator());
        ctx.getSecurityHandler().setAuthMethod("BASIC");
        ctx.getSecurityHandler().setRealmName(("users"));
        ctx.setResourceBase(webappDir);
        server.setHandler(ctx);
    }
    
    public void start() {
        try {
            server.start();
        } catch (Exception ex) {
            Logger.getLogger(FlareJettyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void join() {
        try {
            server.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(FlareJettyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
