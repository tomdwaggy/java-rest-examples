package io.github.arven.rs.hypertext;

import java.util.Collection;
import javax.ws.rs.core.Link;

/**
* Interface to mark objects that are identifiable by links. These links will
* generally include a single self link, which is the identifier of the object
* itself, as well as multiple links to various relations.
*
* @author Brian Becker
*/
public interface HyperlinkIdentifier {
    
    /**
    * Get the hypertext link collection, which can be used to inject links.
    * These links are used for identification of various resources which are
    * related to the main resource.
    * 
    * @return A collection of links, which may include "self"
    */ 
    public abstract Collection<Link> getLinks();
    
}