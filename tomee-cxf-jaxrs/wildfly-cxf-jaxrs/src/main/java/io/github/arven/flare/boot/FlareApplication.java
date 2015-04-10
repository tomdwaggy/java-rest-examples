/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.arven.rs.services.example.MicroBlogApplication;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;
import javax.ejb.embeddable.EJBContainer;
import javax.enterprise.inject.spi.CDI;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.openejb.loader.JarLocation;
import org.apache.openejb.util.NetworkUtil;
import org.apache.tomee.embedded.Configuration;
import org.apache.tomee.embedded.Container;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The FlareApplication main class, which starts the web application,
 * loads configuration from the main class, and loads a few beans from
 * the container which will be used in configuration.
 * 
 * @author Brian Becker
 */
public class FlareApplication {
    
    private static final Logger log = LoggerFactory.getLogger(FlareApplication.class.getCanonicalName());
    
    private Properties applicationProperties;
    private FlareBootApplication applicationConfigBoot;
    private Package applicationConfigPackage;
    private Object application;
           
    public void run(String[] args) {
        try {
            
            final Container container = new Container(
                new Configuration()
                .http(8080)
                .property("openejb.container.additional.exclude", "org.apache.tomee.embedded.")
                .property("openejb.additional.include", "tomee-"))
                .deployPathsAsWebapp(JarLocation.jarLocation(FlareApplication.class))
                .inject(this);
            
            container.await();
            /*applicationProperties = new Properties();
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

            EJBContainer ejbs = EJBContainer.createEJBContainer();
            
            Set<Method> naming = reflections.getMethodsAnnotatedWith(NamingConfiguration.class);
            for(Method m : naming) {
                Context ic = new InitialContext();
                try {
                    ic.createSubcontext("java:comp/env/");
                } catch(NamingException ex) {}
                log.warn(m.getName());
                m.invoke(application, ic);
            }            
            
            FlareJettyServer server = (FlareJettyServer) CDI.current().select(Class.forName(applicationProperties.getProperty("flare.container.server"))).get();
            
            server.setWebAppContext(applicationConfigBoot.value());
            server.setWebAppDir(applicationConfigBoot.resources());
            server.setPackage(applicationConfigPackage);
            server.setConfiguration(application);
            
            server.init();
            server.start();
            
            log.warn("JETTY STARTED");
            
            Set<Method> flare = reflections.getMethodsAnnotatedWith(FlareConfiguration.class);
            for(Method m : flare) {
                log.warn("FLARE CONFIGURING " + m.getName());
                m.invoke(application);
            }
            
            //CXFCdiServlet srv = (CXFCdiServlet) server.xservlet;
            //ObjectMapper obmap = new ObjectMapper();
            //System.out.println(srv.getInitParameterNames());
            
            server.join();*/
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
