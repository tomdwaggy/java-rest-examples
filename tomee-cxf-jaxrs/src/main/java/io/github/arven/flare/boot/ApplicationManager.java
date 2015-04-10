package io.github.arven.flare.boot;

import java.util.Collection;
import org.apache.catalina.startup.Tomcat;

/**
 * @author Brian Becker
 */
public class ApplicationManager {
    
    private static ApplicationManager manager;
    private final Tomcat tomcat;
    
    public static ApplicationManager instance() {
        if(manager == null) {
            throw new IllegalStateException("No ApplicationManager registered");
        }
        return manager;
    }
    
    public static void setApplicationManager(ApplicationManager mgr) {
        manager = mgr;
    }
    
    public ApplicationManager(Tomcat tomcat) {
        this.tomcat = tomcat;
    }
    
    public void addUser(String name, String password, Collection<String> roles) {
        this.tomcat.addUser(name, password);
        for(String role : roles) {
            this.tomcat.addRole(name, role);
        }
        System.err.println("Trying to add... " + name + " " + password);
    }
    
}
