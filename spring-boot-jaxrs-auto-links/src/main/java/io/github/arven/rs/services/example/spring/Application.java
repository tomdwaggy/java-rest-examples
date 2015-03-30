package io.github.arven.rs.services.example.spring;


import io.github.arven.rs.services.example.MicroBlogRestResource;
import io.github.arven.rs.provider.NotVeryUsefulAspect;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.inject.Named;
import java.io.Serializable;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Initial Spring Boot Application Configuration
 */

@SpringBootApplication
@ComponentScan("io.github.arven.rs.services.example")
@EntityScan("io.github.arven.rs.services.example")
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application implements Serializable {

    @Named
    public static class JerseyConfig extends ResourceConfig {

        public JerseyConfig() {
            packages("io.github.arven.rs.provider;io.github.arven.rs.services.example");
            this.register(MicroBlogRestResource.class);
            this.register(JacksonFeature.class);
            //this.register(JaxbFeature.class)
        }
    }
    
    @Bean // the Aspect itself must also be a Bean
    public NotVeryUsefulAspect myAspect() {
        return new NotVeryUsefulAspect();
    }    

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }
    
}