/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.boot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.Produces;
import org.apache.openejb.loader.JarLocation;
import org.apache.tomee.embedded.Configuration;
import org.apache.tomee.embedded.Container;

/**
 * The FlareApplication main class, which starts the web application,
 * loads configuration from the main class, and loads a few beans from
 * the container which will be used in configuration.
 * 
 * @author Brian Becker
 */
@ApplicationScoped
public class FlareApplication {
    
    private ApplicationManager loginManager;
        
    public void run(String[] args) {

        try {
            
            FlareAnnotationProcessor annotations = new FlareAnnotationProcessor(this);
            
            annotations.doConfiguration(FlarePreConfiguration.class);
            
            HashMap users = new HashMap();
            
            final Configuration configuration =
                new Configuration()
                .http(8080)
                .property("openejb.container.additional.exclude", "org.apache.tomee.embedded.")
                .property("openejb.additional.include", "tomee-");
            
            final Container container =
                new Container(configuration).deployClasspathAsWebApp().inject(this);
            
            ApplicationManager.setApplicationManager(new ApplicationManager(container.getTomcat()));
            ApplicationManager.instance().addUser("anonymous", "anonymous", Arrays.asList("Anonymous"));
                        
            annotations.doConfiguration(FlarePostConfiguration.class);
            
            container.await();
            
        } catch (Exception ex) {
            Logger.getLogger(FlareApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @Produces
    public ApplicationManager getApplicationManager() {
        return this.loginManager;
    }
    
}
