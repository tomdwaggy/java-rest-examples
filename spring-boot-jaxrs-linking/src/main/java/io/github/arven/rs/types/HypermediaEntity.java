/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import org.springframework.util.Assert;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/**
* Base class for DTOs to collect links.
*
* @author Oliver Gierke
*/
public abstract class HypermediaEntity implements Identifiable<String> {
    
    private final List<Link> links;
    
    public HypermediaEntity() {
        this.links = new ArrayList<Link>();
    }
    /**
    * Adds the given link to the resource.
    *
    * @param link
    */
    public void add(Link link) {
        Assert.notNull(link, "Link must not be null!");
        this.links.add(link);
    }
    /**
    * Adds all given {@link Link}s to the resource.
    *
    * @param links
    */
    public void add(Iterable<Link> links) {
        Assert.notNull(links, "Given links must not be null!");
        for (Link candidate : links) {
            add(candidate);
        }
    }
    /**
    * Adds all given {@link Link}s to the resource.
    *
    * @param links must not be {@literal null}.
    */
    public void add(Link... links) {
        Assert.notNull(links, "Given links must not be null!");
        add(Arrays.asList(links));
    }

    /**
    * Returns all {@link Link}s contained in this resource.
    *
    * @return
    */
    @XmlElement(name = "link")
    @JsonProperty("links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)    
    public List<Link> getLinks() {
        return links;
    }
    /**
    * Removes all {@link Link}s added to the resource so far.
    */
    public void removeLinks() {
        this.links.clear();
    }
    /**
    * Returns the link with the given rel.
    *
    * @param rel
    * @return the link with the given rel or {@literal null} if none found.
    */
    public Link getLink(String rel) {
        for (Link link : links) {
        if (link.getRel().equals(rel)) {
        return link;
        }
        }
        return null;
    }
    /*
    * (non-Javadoc)
    * @see java.lang.Object#toString()
    */
    @Override
    public String toString() {
        return String.format("links: %s", links.toString());
    }
    /*
    * (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
        return true;
        }
        if (obj == null || !obj.getClass().equals(this.getClass())) {
        return false;
        }
        HypermediaEntity that = (HypermediaEntity) obj;
        return this.links.equals(that.links);
    }
    /*
    * (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return this.links.hashCode();
    }
}