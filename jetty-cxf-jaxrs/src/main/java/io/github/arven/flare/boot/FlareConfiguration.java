/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.boot;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;

import javax.inject.Qualifier;

@Qualifier
@Retention(RUNTIME)
@Target({METHOD})
public @interface FlareConfiguration {}