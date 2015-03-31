package io.github.arven.rs.types;

import com.google.common.collect.Lists;
import io.github.arven.rs.services.example.Group;
import io.github.arven.rs.services.example.Message;
import io.github.arven.rs.services.example.Person;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Brian Becker
 * @param <ResponseType>
 */
@XmlRootElement(name = "api")
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({Person.class, Group.class, Message.class})
public class Hyper<ResponseType> {
    
    @XmlAttribute   private Integer offset = null;
    @XmlAttribute   private Boolean reverse = false;
    @XmlAttribute   private Integer limit = null;
    private UriBuilder self = null;
    private List<String> eachActions = new LinkedList<String>();
    
    @XmlAttribute
    public Integer getSize() {
        if(this.content != null) {
            return this.content.size();
        } else {
            return 0;
        }
    }
    
    @XmlElement(name = "link")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    private final Collection<Link> links = new LinkedList<Link>();
    
    @XmlAnyElement
    private List<ResponseType> content;
    
    public Hyper() {
    }
    
    public static class Builder<ResponseType> {
        
        private final Hyper<ResponseType> response = new Hyper();
        
        public Builder() {
            
        }
        
        public Builder<ResponseType> entityList(List<ResponseType> entity) {
            response.content = entity;
            return this;
        }
        
        public Builder<ResponseType> entity(ResponseType entity) {
            response.content = Arrays.asList(entity);
            return this;
        }

        public Builder<ResponseType> link(Link link) {
            if(link.getRels().contains("self")) {
                response.self = link.getUriBuilder();
            }
            response.links.add(link);
            return this;
        }

        public Builder<ResponseType> offset(Integer offset) {
            response.offset = offset;
            if(offset != null && offset < response.content.size()) {
                response.content = response.content.subList(offset, response.content.size());
            }
            return this;
        }

        public Builder<ResponseType> limit(Integer limit) {
            response.limit = limit;
            if(limit != null && limit > 0) {
                response.content = response.content.subList(0, Math.min(limit, response.content.size()));
            } else {
                response.content = Collections.emptyList();
            }
            return this;
        }   

        public Builder<ResponseType> reverse(Boolean reverse) {
            response.reverse = reverse;
            if(reverse != null && reverse == true) {
                response.content = Lists.reverse(response.content);
            }
            return this;
        }
        
        public Builder<ResponseType> each(String... action) {
            response.eachActions.addAll(Arrays.asList(action));
            return this;
        }

        public Hyper<ResponseType> build() {
            if(response.self != null) {
                System.out.println("<<<<<<< NONEMPTY RESPONSE >>>>>>>");
                for(Object o : response.content) {
                    if(Linked.class.isInstance(o)) {
                        System.out.println("<<<<<< ADDDING LINKS >>>>>>");
                        Linked r = (Linked) o;
                        r.getLinks().clear();
                        Link.Builder lb;
                        if(response.content.size() > 1) {
                            lb = Link.fromUriBuilder(response.self.clone().path(r.getId()));
                        } else {
                            lb = Link.fromUriBuilder(response.self.clone());
                        }
                        for(String s : response.eachActions) {
                            lb.rel(s);
                        }
                        r.getLinks().add(lb.rel("self").build());
                        System.out.println(r);
                    }
                }
            }
            return response;
        }
        
    }    
    
}
