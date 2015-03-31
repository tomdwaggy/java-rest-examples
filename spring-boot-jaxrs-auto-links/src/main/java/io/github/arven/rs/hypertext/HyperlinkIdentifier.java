package io.github.arven.rs.hypertext;

import java.lang.reflect.Method;
import java.util.Collection;
import javax.ws.rs.core.Link;

/**
* Interface to mark objects that are identifiable by an ID of any type.
*
* @author Oliver Gierke
 * @param <ID>
*/
public interface HyperlinkIdentifier {
    /**
    * Returns the id identifying the object.
    *
    * @return the identifier or {@literal null} if not available.
    */ 
    public abstract Collection<Link> getLinks();
    
}