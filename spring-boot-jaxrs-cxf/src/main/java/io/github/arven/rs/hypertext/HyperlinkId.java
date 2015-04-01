package io.github.arven.rs.hypertext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
/**
 * This identifier marks the method or field which represents the identifier
 * which should replace one of the HyperlinkPath parameters. The default value
 * of this annotation is "id" so it may be included without having to specify
 * it. However, if using multiple identifiers in one path, it may result in
 * cleaner code to specify all identifiers.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface HyperlinkId {

    /**
     * The value specifies the path element which is replaced by the value
     * in the field or method. This path element must be specified in the
     * HyperlinkPath. The default is {id} so the annotation value is not
     * really needed if you only have a single path element to refer to
     * the given object.
     * 
     * @return The path element which this value takes the place of
     */
    public String value() default "id";

}