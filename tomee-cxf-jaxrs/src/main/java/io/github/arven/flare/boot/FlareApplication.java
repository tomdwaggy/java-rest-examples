/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.boot;

import io.github.arven.flare.rs.ApiOriginFilter;
import io.github.arven.flare.rs.HttpBasicAuthenticationFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;
import org.apache.openejb.AppContext;
import org.apache.openejb.core.WebContext;
import org.apache.openejb.loader.JarLocation;
import org.apache.tomee.embedded.Configuration;

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
            
            final TomcatConfiguration configuration =
                new TomcatConfiguration()
                .http(8080)
                .property("openejb.container.additional.exclude", "org.apache.tomee.embedded.")
                .property("openejb.additional.include", "tomee-")
                .filter("http basic", HttpBasicAuthenticationFilter.class.getName())
                .filter("api origin", ApiOriginFilter.class.getName());
            
            final TomcatContainer container =
                new TomcatContainer(configuration).deployPathsAsWebapp(JarLocation.jarLocation(FlareApplication.class));
                                    
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
