package io.github.arven.rs.services.example.weld;

import io.github.arven.flare.ee.WeldFlare;
import io.github.arven.rs.services.example.MicroBlogRestResource;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.CDIProvider;
import javax.enterprise.util.TypeLiteral;
import javax.persistence.Persistence;
import org.apache.cxf.cdi.CXFCdiServlet;
import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.jboss.weld.bootstrap.api.SingletonProvider;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.injection.spi.JpaInjectionServices;

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
        
        //System.out.println(">>>>>>>>>>>>>>>" + container.instance().select(JpaInjectionServices.class));
        
        final Server server = new Server(8080);
        
        WebAppContext ctx = new WebAppContext();
        ctx.setContextPath("/*");
        ctx.addServlet(new ServletHolder(new CXFCdiServlet()), "/*");
        ctx.setResourceBase(".");
        server.setHandler(ctx);
        
        server.start();
        server.join();
        
        weld.shutdown();
    }
    
}