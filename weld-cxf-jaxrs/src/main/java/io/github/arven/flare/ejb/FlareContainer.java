package io.github.arven.flare.ejb;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.reflections.Reflections;
import org.slf4j.LoggerFactory;

/**
 * Created by brian.becker on 4/8/2015.
 */
public class FlareContainer extends EJBContainer {

    private Context ctx;
    
    private static FlareContainer fc;
    
    private FlareStatelessContainer defaultStateless;

    public FlareContainer() {
        try {
            Context init = new InitialContext();
            LoggerFactory.getLogger(FlareContainer.class).info("Flare Container initializing");
            init.createSubcontext("java:global");
            Context flare = init.createSubcontext("java:global/flare/");
            
            defaultStateless = new FlareStatelessContainer("Default Stateless Container", flare);
            
            ctx = init;
            LoggerFactory.getLogger(FlareContainer.class).info("Flare Container fully initialized");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {

    }

    @Override
    public Context getContext() {
        return ctx;
    }

}
