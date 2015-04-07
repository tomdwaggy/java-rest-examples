/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.converter.ModelConverters;
import com.wordnik.swagger.jaxrs.config.ReflectiveJaxrsScanner;
import io.github.arven.flare.ee.WeldFlare;
import io.github.arven.flare.server.jetty.FlareJettyServer;
import io.github.arven.flare.server.FlareServer;

/**
 *
 * @author brian.becker
 */
public class FlareApplication {
        
    public void run(String[] args) {
        try {
            String webappDir = "./src/main/webapp/";
            
            FlareNamingConfiguration naming = new FlareNamingConfiguration();
            naming.configure();
            
            WeldFlare weld = new WeldFlare();
            weld.initialize();

            FlareServer server = new FlareJettyServer(webappDir);
            server.init();
            server.start();

            ReflectiveJaxrsScanner scanner = new ReflectiveJaxrsScanner();
            scanner.setResourcePackage("io.github.arven.rs.services.example");
            ScannerFactory.setScanner(scanner);

            ObjectMapper obMap = new ObjectMapper();
            obMap.setAnnotationIntrospector(new JaxbAnnotationIntrospector(obMap.getTypeFactory()));
            ModelConverters.getInstance().addConverter(new com.wordnik.swagger.jackson.ModelResolver(obMap));

            server.join();

            weld.shutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
