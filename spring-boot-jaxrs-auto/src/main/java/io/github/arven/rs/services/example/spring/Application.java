package io.github.arven.rs.services.example.spring;


import io.github.arven.rs.services.example.MicroBlogRestResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.inject.Named;
import java.io.Serializable;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * Initial Spring Boot Application Configuration
 */

@SpringBootApplication
@ComponentScan("io.github.arven.rs.services.example")
@EntityScan("io.github.arven.rs.services.example")
public class Application implements Serializable {

    @Named
    @ApplicationPath("/example")
    public static class JerseyConfig extends ResourceConfig {

        public JerseyConfig() {
            //packages("io.github.arven.rs.provider;io.github.arven.rs.services.example");
            this.register(MicroBlogRestResource.class);
            this.register(JacksonFeature.class);
        }
        
    }
        
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}