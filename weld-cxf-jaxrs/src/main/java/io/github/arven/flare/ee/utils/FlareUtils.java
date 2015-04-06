/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.ee.utils;

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
    
}
