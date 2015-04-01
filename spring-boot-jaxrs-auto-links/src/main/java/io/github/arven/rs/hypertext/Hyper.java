package io.github.arven.rs.hypertext;

import com.google.common.collect.Lists;
import io.github.arven.rs.services.example.Group;
import io.github.arven.rs.services.example.Message;
import io.github.arven.rs.services.example.Person;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
 * The Hyper class is a wrapper for most data types, including lists, which
 * provides a convenient way to bootstrap the links in return values as well
 * as rewrite the lists. It supports injection into classes which implement
 * HyperlinkIdentifier, as well as gets its data from two annotations: the
 * HyperlinkPath class annotation, and the HyperlinkId method and field
 * annotation.
 * 
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
    private String type = "application/xml";
    private Class<?> matcher = null;
    private URI method = URI.create("/");
    
    @XmlAttribute
    public Integer getSize() {
        if(this.content != null) {
            return this.content.size();
        } else {
            return 0;
        }
    }
    
    @XmlElement(name = "link", namespace = "http://github.com/Arven/java-rest-examples/hypertext")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)           
    private final Collection<Link> links = new HashSet<Link>();
    
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
        
        public Builder<ResponseType> type(String type) {
            response.type = type;
            return this;
        }

        public Builder<ResponseType> self(Link link) {
            response.self = link.getUriBuilder();
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
        
        public Builder<ResponseType> matcher(Class matched) {
            response.matcher = matched;
            return this;
        }
        
        public Builder<ResponseType> method(URI method) {
            response.method = method;
            return this;
        }
        
        public static void buildLinks(Method m, Collection<Link> links, URI from, String newpath, Map<String, Object> params, String type, URI ignoreGet) {
            URI newURI = UriBuilder.fromUri(from).path(newpath).buildFromMap(params);
            if(!newURI.equals(ignoreGet) && m.isAnnotationPresent(GET.class) && (List.class.isAssignableFrom(m.getReturnType()) || ListView.class.isAssignableFrom(m.getReturnType()))) {
                links.add(Link.fromUri(newURI).rel("list").title(m.getName()).type(type).build());
            } else if(!newURI.equals(ignoreGet) & m.isAnnotationPresent(GET.class)) {
                links.add(Link.fromUri(newURI).rel("self").title(m.getName()).type(type).build());
            } else if(m.isAnnotationPresent(DELETE.class)) {
                links.add(Link.fromUri(newURI).rel("remove").title(m.getName()).type(type).build());
            } else if(m.isAnnotationPresent(POST.class)) {
                links.add(Link.fromUri(newURI).rel("add").title(m.getName()).type(type).build());
            } else if(m.isAnnotationPresent(PUT.class)) {
                links.add(Link.fromUri(newURI).rel("update").title(m.getName()).type(type).build());
            } 
        }
        
        public Hyper<ResponseType> build() {
            if(response.self != null && response.matcher != null) {
                for(Object o : response.content) {
                    HyperlinkPath id = (HyperlinkPath) o.getClass().getAnnotation(HyperlinkPath.class);
                    Map<String, Object> params = HyperlinkUtils.getHyperlinkValues(o);
                    List<Link> links = new LinkedList<Link>();
                    URI self = UriBuilder.fromPath(id.value()).buildFromMap(params);
                    links.add(Link.fromUri(self).type(response.type).rel("self").build());
                    for(Method m : response.matcher.getSuperclass().getMethods()) {
                        try {
                            if(m.isAnnotationPresent(Hyperlinked.class)) {
                                URI relpath = URI.create("/");
                                if(m.isAnnotationPresent(Path.class)) {
                                    relpath = UriBuilder.fromPath("/").path(((Path)m.getAnnotation(Path.class)).value()).resolveTemplates(params).build();
                                }
                                String newpath = response.method.relativize(relpath).getPath();
                                System.out.println(m.getName() + "  " + newpath + " ( method = " + response.method.getPath() + ", rel = " + relpath + ")");
                                if(newpath.equals("")) {
                                    buildLinks(m, response.links, response.self.build(), newpath, params, response.type, null);
                                } else if(!newpath.equals("") && !newpath.startsWith("/")) {
                                    buildLinks(m, links, response.self.build(), newpath, params, response.type, self);
                                }
                            }
                        } catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
                    }
                    HyperlinkUtils.injectHyperlinks(o, links);
                }        
            }
            return response;
        }
        
    }    
    
}
