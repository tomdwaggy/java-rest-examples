/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.server.jetty;

import io.github.arven.flare.boot.FlareServlet;
import io.github.arven.flare.server.FlareServer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.util.AnnotationLiteral;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.jboss.weld.SimpleCDI;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

/**
 * @author Brian Becker
 */
public class FlareJettyServer implements FlareServer {
    
    private final String webappDir;
    private final String webappPath;
    private final Package pkg;
    private final Object config;
    private Server server;
    
    public FlareJettyServer(String webappDir, String webappPath, Package pkg, Object config) {
        this.webappDir = webappDir;
        this.webappPath = webappPath;
        this.pkg = pkg;
        this.config = config;
    }
    
    public void init() {
        HashLoginService login = SimpleCDI.current().select(HashLoginService.class, new AnnotationLiteral<JettyService>() {}).get();
        
        server = new Server(8080);
        WebAppContext ctx = new WebAppContext();
        ctx.setContextPath(this.webappPath);
        
        Reflections reflections = new Reflections(pkg.getName(), new MethodAnnotationsScanner());
        for(Method m : reflections.getMethodsAnnotatedWith(FlareServlet.class)) {
            try {
                Servlet s = (Servlet) m.invoke(config);
                FlareServlet info = m.getAnnotation(FlareServlet.class);
                ctx.addServlet(new ServletHolder(s), info.value());
                for(Class<? extends Filter> f : info.filters()) {
                    ctx.addFilter(f, info.value(), EnumSet.of(DispatcherType.REQUEST));
                }
            } catch (IllegalAccessException ex) {
                Logger.getLogger(FlareJettyServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(FlareJettyServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(FlareJettyServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
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
