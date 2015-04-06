/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.ee;

import io.github.arven.flare.ee.utils.FlareUtils;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.jboss.weld.injection.spi.JpaInjectionServices;
import org.jboss.weld.injection.spi.ResourceReference;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;
import org.jboss.weld.injection.spi.helpers.SimpleResourceReference;

/**
 * Flare Embedded Framework JPA Support Class
 * 
 * @author Brian Becker
 */
public class FlarePersistenceInjector implements JpaInjectionServices {
    
    private final Map<String, EntityManager> managers = new HashMap<String, EntityManager>();

    public ResourceReferenceFactory<EntityManager> registerPersistenceContextInjectionPoint(final InjectionPoint injectionPoint) {
        return new ResourceReferenceFactory<EntityManager>() {

            public ResourceReference<EntityManager> createResource() {
                PersistenceContext pu = injectionPoint.getAnnotated().getAnnotation(PersistenceContext.class);
                if(!managers.containsKey(pu.unitName())) {
                    managers.put(pu.unitName(), Persistence.createEntityManagerFactory(FlareUtils.emptyNull(pu.unitName())).createEntityManager());
                }
                return new SimpleResourceReference<EntityManager>(managers.get(pu.unitName()));
            }
            
        };
    }

    public ResourceReferenceFactory<EntityManagerFactory> registerPersistenceUnitInjectionPoint(final InjectionPoint injectionPoint) {
        return new ResourceReferenceFactory<EntityManagerFactory>() {

            public ResourceReference<EntityManagerFactory> createResource() {
                PersistenceUnit pu = injectionPoint.getAnnotated().getAnnotation(PersistenceUnit.class);
                return new SimpleResourceReference<EntityManagerFactory>(Persistence.createEntityManagerFactory(FlareUtils.emptyNull(pu.unitName())));
            }
            
        };
    }

    public EntityManager resolvePersistenceContext(InjectionPoint injectionPoint) {
        return registerPersistenceContextInjectionPoint(injectionPoint).createResource().getInstance();
    }

    public EntityManagerFactory resolvePersistenceUnit(InjectionPoint injectionPoint) {
        return registerPersistenceUnitInjectionPoint(injectionPoint).createResource().getInstance();
    }

    public void cleanup() {
        // No cleanup required for embedded Flare
    }
    
}