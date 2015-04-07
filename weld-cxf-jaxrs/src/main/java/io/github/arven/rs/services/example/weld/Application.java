package io.github.arven.rs.services.example.weld;

import io.github.arven.flare.ee.WeldFlare;
import io.github.arven.rs.services.example.MicroBlogApplication;
import io.github.arven.rs.services.example.MicroBlogRestResource;
import io.github.arven.rs.services.example.UserRestResource;
import org.apache.cxf.cdi.CXFCdiServlet;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.security.SecureAnnotationsInterceptor;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * This Weld Boot application configuration simply defines Jersey and
 * prepares the entire Spring application for startup.
 * 
 * @author Brian Becker
 */
public class Application {
    
    public static void main(String[] args) throws Exception {
        System.setProperty("java.naming.factory.url", "org.eclipse.jetty.jndi");
        System.setProperty("java.naming.factory.initial", "org.eclipse.jetty.jndi.InitialContextFactory");
        
        WeldFlare weld = new WeldFlare();
        WeldContainer container = weld.initialize();
        
        HashLoginService login = container.instance().select(HashLoginService.class).get();
                
        final Server server = new Server(8080);
        
        WebAppContext ctx = new WebAppContext();
        ctx.setContextPath("/*");
        
        CXFCdiServlet cxf = new CXFCdiServlet();
       
        ctx.addServlet(new ServletHolder(cxf), "/*");
        
        ctx.getSecurityHandler().setLoginService(login);
        ctx.getSecurityHandler().setAuthenticator(new BasicAuthenticator());
        ctx.getSecurityHandler().setAuthMethod("BASIC");
        ctx.getSecurityHandler().setRealmName(("users"));
        
        ctx.setResourceBase(".");
        server.setHandler(ctx);
        
        server.start();        
        server.join();
        
        weld.shutdown();
    }
    
}