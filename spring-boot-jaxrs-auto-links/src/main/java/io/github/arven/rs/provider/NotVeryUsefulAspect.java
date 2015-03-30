/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.provider;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class NotVeryUsefulAspect {
    
    @Pointcut("within(io.github.arven.rs.services.example..*)")
    private void anyPublicOperation() {}

    @AfterReturning("anyPublicOperation()")
    public void doAccessCheck() {
        System.out.println("<<<<<<<<<<< RETURNED >>>>>>>>>>>>>");
    }    
    
}