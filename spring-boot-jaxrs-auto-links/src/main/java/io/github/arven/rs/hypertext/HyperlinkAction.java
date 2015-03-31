/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.hypertext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface HyperlinkAction {
 
    public enum Method {
        DELETE, GET, HEAD, OPTIONS, POST, PUT
    }
    
    //should ignore this test?
    public String value();
    public String target() default "";
    public Method method() default Method.GET;

}