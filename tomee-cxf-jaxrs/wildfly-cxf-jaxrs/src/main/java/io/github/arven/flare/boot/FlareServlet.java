/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.boot;

import io.github.arven.flare.rs.ApiOriginFilter;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import javax.enterprise.util.Nonbinding;

import javax.inject.Qualifier;
import javax.servlet.Filter;

@Qualifier
@Retention(RUNTIME)
@Target({METHOD})
public @interface FlareServlet {
    public String value() default "/*";
    @Nonbinding public Class<? extends Filter>[] filters() default { ApiOriginFilter.class };
}