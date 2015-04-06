/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.services.example;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.github.arven.rs.provider.JacksonJaxbYamlProvider;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class MicroBlogApplication extends Application {
    @Inject private MicroBlogRestResource restService;
    
    private JacksonJaxbJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider();
    private JacksonJaxbYamlProvider jacksonYamlProvider = new JacksonJaxbYamlProvider();

    @Produces 
    @Override
    public Set<Object> getSingletons() {
        return new HashSet<Object>(
            Arrays.asList(
                restService,
                jacksonJsonProvider,
                jacksonYamlProvider
            )
        );
    }
    
}