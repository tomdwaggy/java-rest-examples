package io.github.arven.flare.ejb;

import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.ejb.spi.EJBContainerProvider;
import javax.naming.NamingException;
import java.util.Map;

/**
 * Created by brian.becker on 4/8/2015.
 */
public class FlareContainerProvider implements EJBContainerProvider {

    public EJBContainer createEJBContainer(Map<?, ?> map) throws EJBException {
        return new FlareContainer();
    }

    public EJBContainer createEJBContainer() throws EJBException, NamingException {
        return new FlareContainer();
    }

}
