package io.github.arven.flare.ee;

import io.github.arven.flare.utils.FlareUtils;
import java.lang.reflect.Type;
import javax.enterprise.inject.spi.Annotated;
import org.jboss.weld.SimpleCDI;
import org.jboss.weld.injection.spi.ResourceReference;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;
import org.jboss.weld.injection.spi.helpers.SimpleResourceReference;

/**
 * @author Brian Becker
 * @param <T>
 */
public class FlareResourceInstanceFactory<T> implements ResourceReferenceFactory<T> {

    private final Type type;
    private final Annotated ann;
    
    public FlareResourceInstanceFactory(Type type, Annotated ann) {
        this.type = type;
        this.ann = ann;
    }
    
    public ResourceReference<T> createResource() {
        return new SimpleResourceReference<T>(
                (T) SimpleCDI.current().select(FlareUtils.getType(this.type),
                    FlareUtils.getQualifiers(this.ann)).get()
        );
    }
    
}
