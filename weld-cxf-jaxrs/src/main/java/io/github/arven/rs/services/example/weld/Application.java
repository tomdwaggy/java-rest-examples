package io.github.arven.rs.services.example.weld;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.wordnik.swagger.config.Scanner;
import com.wordnik.swagger.jaxrs.config.ReflectiveJaxrsScanner;
import io.github.arven.flare.boot.FlareApplication;
import io.github.arven.flare.boot.FlareBootApplication;
import io.github.arven.flare.boot.FlareConfiguration;
import io.github.arven.flare.boot.FlareServlet;
import io.github.arven.flare.boot.NamingConfiguration;
import io.github.arven.flare.rs.ApiOriginFilter;
import javax.enterprise.inject.Produces;

import javax.naming.Context;
import javax.servlet.Servlet;
import org.apache.cxf.cdi.CXFCdiServlet;

/**
 * This Weld Boot application configuration simply defines Jersey and
 * prepares the entire Spring application for startup.
 * 
 * @author Brian Becker
 */
@FlareBootApplication("/*")
public class Application {
    
    public static void main(String[] args) {
        FlareApplication app = new FlareApplication();
        app.run(args);
    }
    
    @NamingConfiguration
    public void configureNaming(Context context) throws Exception {
        context.bind("java:comp/env/mySpecialValue", 4000);
    }
    
    @Produces @FlareConfiguration
    public Scanner configureSwagger() {
        ReflectiveJaxrsScanner scanner = new ReflectiveJaxrsScanner();
        scanner.setResourcePackage("io.github.arven.rs.services.example");
        return scanner;
    }
    
    @Produces @FlareConfiguration
    public ObjectMapper configureJackson() {
        ObjectMapper obMap = new ObjectMapper();
        obMap.setAnnotationIntrospector(new JaxbAnnotationIntrospector(obMap.getTypeFactory()));        
        return obMap;
    }
    
    @FlareServlet("/example/*")
    public Servlet jaxrsServlet() {
        return new CXFCdiServlet();
    }
    
}