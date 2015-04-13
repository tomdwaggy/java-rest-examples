package io.github.arven.rs.services.example.flare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.converter.ModelConverters;
import com.wordnik.swagger.jackson.ModelResolver;
import com.wordnik.swagger.jaxrs.config.ReflectiveJaxrsScanner;

import io.github.arven.flare.boot.FlareApplication;
import io.github.arven.flare.boot.FlareBootConfiguration;
import io.github.arven.flare.boot.FlarePostConfiguration;

import javax.naming.NamingException;
import org.apache.openejb.jee.Module;

/**
 * This Flare Boot application configuration simply defines CXF and
 * prepares the entire Weld application for startup.
 * 
 * @author Brian Becker
 */
@FlareBootConfiguration
public class Application {

    public static void main(String[] args) throws NamingException {
        FlareApplication app = new FlareApplication();
        app.run(args);
    }
    
    @FlarePostConfiguration
    public void configureSwagger() {
        ReflectiveJaxrsScanner scanner = new ReflectiveJaxrsScanner();
        scanner.setResourcePackage("io.github.arven.rs.services.example");
        ObjectMapper obMap = new ObjectMapper();
        obMap.setAnnotationIntrospector(new JaxbAnnotationIntrospector(obMap.getTypeFactory()));
        ScannerFactory.setScanner(scanner);
        ModelConverters.getInstance().addConverter(new ModelResolver(obMap));
    }
    
}