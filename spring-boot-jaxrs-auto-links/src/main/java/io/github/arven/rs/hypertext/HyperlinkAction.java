package io.github.arven.rs.hypertext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
/**
 * The HyperlinkAction allows the user to specify extra actions. These can
 * be links to lists or individual objects which are related to the given
 * object. This allows for linking between objects, rather than just providing
 * a link to the object's own identity.
 * 
 * @author Brian Becker
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface HyperlinkAction {
 
    /**
     * This represents an HTTP Method, which is available to be performed
     * given the action is available. The default Method is GET, which does
     * not need to be specified but may be for clarity.
     */
    public enum Method {
        DELETE, GET, HEAD, OPTIONS, POST, PUT
    }
    
    /**
     * This represents a HyperlinkAction value, which is the rel link text
     * for the given action. It should be clear and concise so it is obvious
     * what sort of action is to be performed on the target.
     * 
     * @return rel link text
     */
    public String value();
    
    /**
     * The target is not required to be set. By default it refers to a target
     * with the same value as the value() property. This makes a reference to
     * the same path as the rel link text.
     * 
     * @return path to object action
     */
    public String target() default "";
    
    /**
     * The method is not required to be set. By default it refers to the GET
     * request, which is most useful for searches, listings, and simple object
     * views. An additional HyperlinkAction must be created if there are
     * multiple actions at one path.
     * 
     * @return 
     */
    public Method method() default Method.GET;

}