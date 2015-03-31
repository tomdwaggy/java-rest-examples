package io.github.arven.rs.types;

import java.util.Collection;

/**
* Interface to mark objects that are identifiable by an ID of any type.
*
* @author Oliver Gierke
 * @param <ID>
*/
public interface Linked<LinkType> {
    /**
    * Returns the id identifying the object.
    *
    * @return the identifier or {@literal null} if not available.
    */ 
    public abstract Collection<LinkType> getLinks();
    public abstract String getId();
}