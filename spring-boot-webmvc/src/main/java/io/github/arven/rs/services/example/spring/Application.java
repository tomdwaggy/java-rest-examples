package io.github.arven.rs.services.example.spring;


import io.github.arven.rs.services.example.MicroBlogRestResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.inject.Named;
import java.io.Serializable;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * This Spring Boot application configuration simply defines Jersey and
 * prepares the entire Spring application for startup.
 * 
 * @author Brian Becker
 */
@SpringBootApplication
@ComponentScan("io.github.arven.rs.services.example")
@EntityScan("io.github.arven.rs.services.example")
@Configuration
public class Application implements Serializable {

    @Named
    public static class JerseyConfig extends ResourceConfig {
        public JerseyConfig() {
            packages("io.github.arven.rs.provider;io.github.arven.rs.services.example");
            this.register(MicroBlogRestResource.class);
            this.register(JacksonFeature.class);
        }
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }
    
}