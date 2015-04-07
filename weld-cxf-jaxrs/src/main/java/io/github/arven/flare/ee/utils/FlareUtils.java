/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.ee.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.inject.spi.Annotated;
import javax.inject.Qualifier;

/**
 *
 * @author brian.becker
 */
public class FlareUtils {
    
    public static String emptyNull(String unitName) {
        if(unitName.equals("")) {
            return null;
        } else {
            return unitName;
        }
    }  
    
    public static Class<?> getType(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return getType(((ParameterizedType) type).getRawType());
        } else {
            throw new RuntimeException("Could not determine type of object");
        }
    }
    
    public static Annotation[] getQualifiers(Annotated ann) {
        List<Annotation> quals = new LinkedList<Annotation>();
        for(Annotation a : ann.getAnnotations()) {
            if(a.annotationType().isAnnotationPresent(Qualifier.class)) {
                quals.add(a);
            }
        }
        return quals.toArray(new Annotation[0]);
    }
        
}
