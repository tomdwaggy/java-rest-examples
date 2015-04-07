package io.github.arven.rs.services.example.weld;

import io.github.arven.flare.boot.FlareApplication;
import io.github.arven.flare.boot.FlareBootApplication;

/**
 * This Weld Boot application configuration simply defines Jersey and
 * prepares the entire Spring application for startup.
 * 
 * @author Brian Becker
 */
@FlareBootApplication
public class Application {
    
    public static void main(String[] args) {
        FlareApplication app = new FlareApplication();
        app.run(args);
    }
    
}