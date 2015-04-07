package io.github.arven.rs.services.example.weld;

import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.jaxrs.config.BeanConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.config.ReflectiveJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiListingResource;
import com.wordnik.swagger.models.Swagger;
import io.github.arven.flare.ee.WeldFlare;
import io.github.arven.rs.provider.ApiOriginFilter;
import io.github.arven.rs.services.example.MicroBlogApplication;
import io.github.arven.rs.services.example.MicroBlogRestResource;
import io.github.arven.rs.services.example.UserRestResource;
import java.io.IOException;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.apache.cxf.cdi.CXFCdiServlet;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.security.SecureAnnotationsInterceptor;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.RoleInfo;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.UserIdentity;
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
        
        HashLoginService login = container.instance().select(HashLoginService.class).get();
                
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
        
        BeanConfig cfg = new BeanConfig();
        cfg.setResourcePackage("io.github.arven.rs.provider");
        cfg.setVersion("1.0.0");
        cfg.setBasePath("");
        cfg.setTitle("MicroBlog Example App");
        cfg.setDescription("Example JAX-RS Weld App");
        cfg.setContact("arven@arven.github.io");
        cfg.setScan(true);
        
        ReflectiveJaxrsScanner scanner = new ReflectiveJaxrsScanner();
        scanner.setResourcePackage("io.github.arven.rs.services.example");
        ScannerFactory.setScanner(scanner);
        
        server.join();
        
        weld.shutdown();
    }
    
}