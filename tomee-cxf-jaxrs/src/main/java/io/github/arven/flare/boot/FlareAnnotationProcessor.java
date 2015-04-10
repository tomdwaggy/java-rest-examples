/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.boot;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.reflections.Reflections;

/**
 *
 * @author brian.becker
 */
public class FlareAnnotationProcessor {
    
    private final FlareApplication app;
    private final Set<Class<?>> configurations;
    
    public FlareAnnotationProcessor(FlareApplication app) {
        Reflections reflections = new Reflections();
        this.configurations = reflections.getTypesAnnotatedWith(FlareBootConfiguration.class);
        this.app = app;
    }
    
    public void doConfiguration(Class<? extends Annotation> ann) {
        for(Class<?> config : configurations) {
            try {
                Object instance = config.newInstance();
                Method[] postBootMethods = config.getMethods();
                for(Method method : postBootMethods) {
                    if(method.isAnnotationPresent(FlarePreConfiguration.class)) {
                        method.invoke(instance);
                    }
                }
            } catch (InstantiationException ex) {
                Logger.getLogger(FlareAnnotationProcessor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(FlareAnnotationProcessor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(FlareAnnotationProcessor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(FlareAnnotationProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
