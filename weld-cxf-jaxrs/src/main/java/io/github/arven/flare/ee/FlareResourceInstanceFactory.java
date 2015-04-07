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
public class FlareResourceInstanceFactory<T> implements ResourceReferenceFactory<T> {

    private final Type type;
    
    public FlareResourceInstanceFactory(Type type) {
        this.type = type;
    }
    
    public ResourceReference<T> createResource() {
        return new SimpleResourceReference<T>((T)SimpleCDI.current().select(FlareUtils.getType(this.type)).get());
    }
    
}
