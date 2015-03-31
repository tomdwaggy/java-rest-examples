package io.github.arven.rs.hypertext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The HyperlinkPath can only be defined on a class, and its only value is
 * a parameter-enabled resource locator. It is global to the application,
 * and all objects in a given project should have different locators.
 * 
 * @author Brian Becker
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HyperlinkPath {

    /**
     * This returns the value of the HyperlinkPath, designed to be parsed
     * by the link response filter.
     * 
     * @return a parameter-enabled resource locator
     */
    public String value();

}