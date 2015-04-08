/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordnik.swagger.config.Scanner;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.converter.ModelConverters;
import io.github.arven.flare.ee.WeldFlare;
import io.github.arven.flare.server.jetty.FlareJettyServer;
import io.github.arven.flare.server.FlareServer;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;
import javax.enterprise.util.AnnotationLiteral;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.weld.environment.se.WeldContainer;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

/**
 * The FlareApplication main class, which starts the web application,
 * loads configuration from the main class, and loads a few beans from
 * the Weld container which will be used in configuration.
 * 
 * @author Brian Becker
 */
public class FlareApplication {
    
    private Properties applicationProperties;
    private FlareBootApplication applicationConfigBoot;
    private Package applicationConfigPackage;
    private Object application;
           
    public void run(String[] args) {
        try {
            applicationProperties = new Properties();
            applicationProperties.load(FlareApplication.class.getResourceAsStream("/application.properties"));
            
            Reflections reflections = new Reflections("");
            Set<Class<?>> cls = reflections.getTypesAnnotatedWith(FlareBootApplication.class);
            if(cls.size() != 1) {
                throw new RuntimeException("You must have a single @FlareBootApplication annotated class");
            }
            Class<?> main = cls.iterator().next();
            applicationConfigBoot = main.getAnnotation(FlareBootApplication.class);
            
            applicationConfigPackage = main.getPackage();
            
            application = main.newInstance();
            
            reflections = new Reflections(applicationConfigPackage.getName(), new MethodAnnotationsScanner());

            Set<Method> naming = reflections.getMethodsAnnotatedWith(NamingConfiguration.class);
            for(Method m : naming) {
                Context ic = new InitialContext();
                try {
                    ic.createSubcontext("java:comp/env/");
                } catch(NamingException ex) {}
                m.invoke(application, ic);
            }

            WeldFlare weld = new WeldFlare();
            WeldContainer container = weld.initialize();

            FlareServer server = (FlareServer) container.instance().select(Class.forName(applicationProperties.getProperty("flare.container.server"))).get();
            
            server.setWebAppContext(applicationConfigBoot.value());
            server.setWebAppDir(applicationConfigBoot.resources());
            server.setPackage(applicationConfigPackage);
            server.setConfiguration(application);
            
            server.init();
            server.start();

            ScannerFactory.setScanner(container.instance().select(Scanner.class, new AnnotationLiteral<FlareConfiguration>() {}).get());
            ModelConverters.getInstance().addConverter(new com.wordnik.swagger.jackson.ModelResolver(container.instance().select(ObjectMapper.class, new AnnotationLiteral<FlareConfiguration>() {}).get()));
            
            server.join();

            weld.shutdown();
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
