package io.github.arven.flare.ee;

import io.github.arven.flare.utils.FlareUtils;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.jboss.weld.injection.spi.JpaInjectionServices;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

/**
 * Flare Embedded Framework JPA Support Class
 * 
 * @author Brian Becker
 */
public class FlarePersistenceInjector implements JpaInjectionServices {
    
    public ResourceReferenceFactory<EntityManager> registerPersistenceContextInjectionPoint(final InjectionPoint injectionPoint) {
        PersistenceContext pu = injectionPoint.getAnnotated().getAnnotation(PersistenceContext.class);
        return new FlareResourceReferenceFactory<EntityManager>(Persistence.createEntityManagerFactory(FlareUtils.emptyNull(pu.unitName())).createEntityManager());
    }

    public ResourceReferenceFactory<EntityManagerFactory> registerPersistenceUnitInjectionPoint(final InjectionPoint injectionPoint) {
        PersistenceUnit pu = injectionPoint.getAnnotated().getAnnotation(PersistenceUnit.class);
        return new FlareResourceReferenceFactory<EntityManagerFactory>(Persistence.createEntityManagerFactory(FlareUtils.emptyNull(pu.unitName())));
    }

    public EntityManager resolvePersistenceContext(InjectionPoint injectionPoint) {
        return registerPersistenceContextInjectionPoint(injectionPoint).createResource().getInstance();
    }

    public EntityManagerFactory resolvePersistenceUnit(InjectionPoint injectionPoint) {
        return registerPersistenceUnitInjectionPoint(injectionPoint).createResource().getInstance();
    }

    public void cleanup() {
        // No cleanup required for embedded server
    }
    
}