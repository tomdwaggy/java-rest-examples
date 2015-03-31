/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.hypertext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The HyperlinkActions allows you to set the allowed actions on a given
 * Hyperlink.
 * 
 * @author Brian Becker
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface InjectHyperlinks {
     
    /**
     * This represents a HyperlinkAction value, which is the rel link text
     * for the given action. It should be clear and concise so it is obvious
     * what sort of action is to be performed on the target.
     * 
     * @return rel link text
     */
    public boolean value() default true;

}
