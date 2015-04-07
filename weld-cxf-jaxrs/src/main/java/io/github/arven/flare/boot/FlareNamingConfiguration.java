/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.boot;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author brian.becker
 */
public class FlareNamingConfiguration {
    
    public void configure() throws NamingException {
        Context ic = new InitialContext();
        ic.createSubcontext("java:comp/env/");
        ic.bind("java:comp/env/mySpecialValue", 4000);
    }
    
}
