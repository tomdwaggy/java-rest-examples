package io.github.arven.rs.services.example.weld;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.converter.ModelConverters;
import com.wordnik.swagger.jaxrs.config.ReflectiveJaxrsScanner;
import io.github.arven.flare.ee.WeldFlare;
import io.github.arven.flare.rs.ApiOriginFilter;
import java.util.EnumSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.DispatcherType;
import org.apache.cxf.cdi.CXFCdiServlet;
import org.eclipse.jetty.plus.jndi.EnvEntry;
import org.eclipse.jetty.plus.jndi.Resource;
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
        String webappDir = "./src/main/webapp/";
        
        System.setProperty("java.naming.factory.url", "org.eclipse.jetty.jndi");
        System.setProperty("java.naming.factory.initial", "org.eclipse.jetty.jndi.InitialContextFactory");
        
        Context ic = new InitialContext();
        ic.createSubcontext("java:comp/env/");
        ic.bind("java:comp/env/mySpecialValue", 4000);
        
        WeldFlare weld = new WeldFlare();
        WeldContainer container = weld.initialize();
        
        HashLoginService login = container.instance().select(HashLoginService.class).get();
                
        final Server server = new Server(8080);
                
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
        
        server.start();        
        
        ReflectiveJaxrsScanner scanner = new ReflectiveJaxrsScanner();
        scanner.setResourcePackage("io.github.arven.rs.services.example");
        ScannerFactory.setScanner(scanner);
        
        ObjectMapper obMap = new ObjectMapper();
        obMap.setAnnotationIntrospector(new JaxbAnnotationIntrospector(obMap.getTypeFactory()));
        ModelConverters.getInstance().addConverter(new com.wordnik.swagger.jackson.ModelResolver(obMap));
                
        server.join();
        
        weld.shutdown();
    }
    
}