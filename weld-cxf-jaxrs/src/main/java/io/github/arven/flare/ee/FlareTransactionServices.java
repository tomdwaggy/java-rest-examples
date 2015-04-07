package io.github.arven.flare.ee;

import javax.transaction.Synchronization;
import javax.transaction.UserTransaction;
import org.jboss.weld.transaction.spi.TransactionServices;

/**
 * @author Brian Becker
 */
public class FlareTransactionServices implements TransactionServices {

    public void registerSynchronization(Synchronization synchronizedObserver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isTransactionActive() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public UserTransaction getUserTransaction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void cleanup() {
        // cleanup not required for embedded server
    }
    
}
