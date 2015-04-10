package io.github.arven.rs.services.example.weld;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.converter.ModelConverters;
import com.wordnik.swagger.jackson.ModelResolver;
import com.wordnik.swagger.jaxrs.config.ReflectiveJaxrsScanner;

import io.github.arven.flare.boot.FlareApplication;
import io.github.arven.flare.boot.FlareBootApplication;
import io.github.arven.flare.boot.FlareConfiguration;
import io.github.arven.flare.boot.FlareServlet;
import io.github.arven.flare.boot.NamingConfiguration;
import io.github.arven.flare.rs.ApiOriginFilter;
import io.github.arven.rs.services.example.MicroBlogApplication;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import javax.ejb.embeddable.EJBContainer;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.apache.openejb.server.rest.OpenEJBRestServlet;
import org.apache.tomee.webservices.CXFJAXRSFilter;

/**
 * This Weld Boot application configuration simply defines CXF and
 * prepares the entire Weld application for startup.
 * 
 * @author Brian Becker
 */
@FlareBootApplication("/*")
public class Application {

    public static void main(String[] args) throws NamingException {
        FlareApplication app = new FlareApplication();
        app.run(args);
    }
    
    @NamingConfiguration
    public void configureNaming(Context context) throws Exception {
        context.bind("java:comp/env/mySpecialValue", 4000);
    }
    
    @FlareConfiguration
    public void configureSwagger() {
        ReflectiveJaxrsScanner scanner = new ReflectiveJaxrsScanner();
        scanner.setResourcePackage("io.github.arven.rs.services.example");
        ObjectMapper obMap = new ObjectMapper();
        obMap.setAnnotationIntrospector(new JaxbAnnotationIntrospector(obMap.getTypeFactory()));
        ScannerFactory.setScanner(scanner);
        ModelConverters.getInstance().addConverter(new ModelResolver(obMap));
    }
    
    @FlareServlet(value = "/*", filters = {CXFJAXRSFilter.class})
    public Servlet jaxrsServlet() {
        return new OpenEJBRestServlet();
    }
    
}