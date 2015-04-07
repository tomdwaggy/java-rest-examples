package io.github.arven.flare.ee;

import org.jboss.weld.bootstrap.api.CDI11Bootstrap;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.ejb.spi.EjbServices;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.injection.spi.EjbInjectionServices;
import org.jboss.weld.injection.spi.JpaInjectionServices;
import org.jboss.weld.injection.spi.ResourceInjectionServices;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.security.spi.SecurityServices;
import org.jboss.weld.transaction.spi.TransactionServices;

/**
 *
 * @author brian.becker
 */
public class WeldFlare extends Weld {
    
    @Override
    protected Deployment createDeployment(ResourceLoader resourceLoader, CDI11Bootstrap bootstrap) {
        Deployment deployment =  super.createDeployment(resourceLoader, bootstrap);
        deployment.getServices().add(SecurityServices.class, new FlareSecurityServices());
        deployment.getServices().add(TransactionServices.class, new FlareTransactionServices());
        deployment.getServices().add(ResourceInjectionServices.class, new FlareResourceInjector());
        deployment.getServices().add(EjbInjectionServices.class, new FlareEnterpriseBeanInjector());
        deployment.getServices().add(JpaInjectionServices.class, new FlarePersistenceInjector());  
        deployment.getServices().add(EjbServices.class, new FlareEnterpriseBeanServices());
        return deployment;
    }
    
}
