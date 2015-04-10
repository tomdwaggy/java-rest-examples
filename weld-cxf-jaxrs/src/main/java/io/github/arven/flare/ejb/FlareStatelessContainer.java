/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.ejb;

import io.github.arven.flare.utils.FlareUtils;
import java.util.Collection;
import java.util.Map;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.naming.Context;
import javax.naming.NamingException;
import org.apache.commons.collections.map.MultiValueMap;
import org.jboss.weld.SimpleCDI;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Brian Becker
 */
public class FlareStatelessContainer {
    
    private final Map<Annotated, Object> stateless;
    private final Context ctx;
    private final String id;
    
    public FlareStatelessContainer(String id, Context ctx) throws NamingException {
        this.stateless = new MultiValueMap();
        this.ctx = ctx;
        this.id = id;
        ctx.bind("DefaultStatelessContainer", this);
        LoggerFactory.getLogger(FlareStatelessContainer.class).info("Flare created Container(id = {})", id);
    }
    
    public boolean hasAnnotated(Annotated cls) {
        return stateless.get(cls) != null;
    }
    
    public Object get(Annotated cls) {
        return stateless.get(cls);
    }
    
    public void add(InjectionPoint ip) {
        //try {
            //System.out.println(FlareUtils.getQualifiers(ip.getAnnotated()));
            //stateless.put(ip.getAnnotated(), SimpleCDI.current().select(FlareUtils.getType(ip.getType()), FlareUtils.getQualifiers(ip.getAnnotated())).get());
            //ctx.bind(ip.getType().toString(), stateless.get(ip.getAnnotated()));
            LoggerFactory.getLogger(FlareStatelessContainer.class).info("Flare created Bean(id = {}) bound at {}", id, ip.getType().toString());
        //} catch (NamingException ex) {
        //    LoggerFactory.getLogger(FlareStatelessContainer.class).info("Flare failed to create Bean(id = {}) due to naming exception", id);
        //}
    }
    
    public void addAll(Collection<InjectionPoint> anns) {
        for(InjectionPoint a : anns) {
            this.add(a);
        }
    }    
    
}
