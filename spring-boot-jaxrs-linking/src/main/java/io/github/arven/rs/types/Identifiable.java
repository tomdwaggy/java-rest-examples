package io.github.arven.rs.types;

/**
* Interface to mark objects that are identifiable by an ID of any type.
*
* @author Oliver Gierke
 * @param <ID>
*/
public interface Identifiable<ID> {
    /**
    * Returns the id identifying the object.
    *
    * @return the identifier or {@literal null} if not available.
    */
    ID getId();
}