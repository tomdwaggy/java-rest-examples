package io.github.arven.flare.ee;

import org.jboss.weld.injection.spi.ResourceReference;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;
import org.jboss.weld.injection.spi.helpers.SimpleResourceReference;

/**
 * @author Brian Becker
 */
public class FlareResourceReferenceFactory<T> implements ResourceReferenceFactory<T> {

    private final T type;
    
    public FlareResourceReferenceFactory(T type) {
        this.type = type;
    }
    
    public ResourceReference<T> createResource() {
        return new SimpleResourceReference<T>(type);
    }
    
}
