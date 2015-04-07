package io.github.arven.rs.services.example.weld;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.converter.ModelConverters;
import com.wordnik.swagger.jaxrs.config.ReflectiveJaxrsScanner;
import io.github.arven.flare.ee.WeldFlare;
import io.github.arven.rs.provider.ApiOriginFilter;
import io.github.arven.rs.services.example.MicroBlogService;
import io.github.arven.rs.services.example.Person;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.apache.cxf.cdi.CXFCdiServlet;
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
        
        WeldFlare weld = new WeldFlare();
        WeldContainer container = weld.initialize();
        //System.out.println(FlareEnvironment.getContainer().instance().select(MicroBlogService.class));
        
        HashLoginService login = container.instance().select(HashLoginService.class).get();
        //MicroBlogService svc = container.instance().select(MicroBlogService.class).get();
        //svc.addUser(new Person("test", "test", "test", "test"));
        //System.out.println(svc.getUser("test"));
                
        final Server server = new Server(8080);
        
        WebAppContext ctx = new WebAppContext();
        
        ctx.setContextPath("/*");
        CXFCdiServlet servlet = new CXFCdiServlet();
        ctx.addServlet(new ServletHolder(servlet), "/example/*");
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