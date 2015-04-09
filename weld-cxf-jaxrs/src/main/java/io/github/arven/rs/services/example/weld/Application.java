package io.github.arven.rs.services.example.weld;

import org.apache.cxf.cdi.CXFCdiServlet;
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

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.Servlet;

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
    
    @FlareServlet("/example/*")
    public Servlet jaxrsServlet() {
        return new CXFCdiServlet();
    }
    
}