/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.ee;

import io.github.arven.flare.ee.utils.FlareUtils;
import java.lang.reflect.Type;
import org.jboss.weld.SimpleCDI;
import org.jboss.weld.injection.spi.ResourceReference;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;
import org.jboss.weld.injection.spi.helpers.SimpleResourceReference;

/**
 * @author Brian Becker
 */
public class FlareResourceReferenceFactory<T> implements ResourceReferenceFactory<T> {

    private final Type type;
    
    public FlareResourceReferenceFactory(Type type) {
        this.type = type;
    }
    
    public ResourceReference<T> createResource() {
        System.out.println(SimpleCDI.current().select(FlareUtils.getType(this.type)).get());
        return new SimpleResourceReference<T>((T)SimpleCDI.current().select(FlareUtils.getType(this.type)).get());
    }
    
}
