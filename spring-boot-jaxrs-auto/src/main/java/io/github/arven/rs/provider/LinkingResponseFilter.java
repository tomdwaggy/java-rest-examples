/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.provider;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import io.github.arven.rs.types.HyperList;
import io.github.arven.rs.types.HypermediaEntity;
import java.io.IOException;
import java.util.List;
import javax.inject.Named;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;

/**
 *
 * @author brian.becker
 */
@Interceptor
@HyperlinkedData
public class LinkingResponseFilter {
    
    public static boolean isListOf(Class c, Object entity) {
        if(List.class.isInstance(entity)) {
            List l = (List) entity;
            if(l.size() > 0) {
                Object o = l.get(0);
                System.out.println(o.getClass().getCanonicalName());
                if(c.isInstance(o)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        System.out.println("<<<<< REPLACING ENTITY >>>>>");
        Object returnValue = ctx.proceed();
        System.out.println("<<<< INTERCEPTING AT ALL >>>>");
        if(HypermediaEntity.class.isInstance(returnValue) || isListOf(HypermediaEntity.class, returnValue)) {
            return new HyperList.Builder(returnValue).build();
        } else {
            return returnValue;
        }
    }

    public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {
        //Type type = Types.createParameterizedType(HyperList.class, res.getEntityType());
        //if(ParameterizedType.class.isInstance(res.getEntityType())) {
        //    System.out.println("<<<<PARAM TYPE>>>>>");
        //    ParameterizedType pt = (ParameterizedType) res.getEntityType();
        //    if(pt.getActualTypeArguments().length == 1) {
        //        type = Types.createParameterizedType(HyperList.class, pt.getActualTypeArguments()[0]);
        //    }
        //}
        
        //System.out.println("TIGER: " + type.toString());
        //if(HypermediaEntity.class.isInstance(res.getEntity()) || isListOf(HypermediaEntity.class, res.getEntity())) { //|| isListOf(HypermediaEntity.class, res.getEntity())) {
        //    System.out.println("<<<<< REPLACING ENTITY >>>>>");
        //    res.setEntity(new HyperList.Builder(res.getEntity())
        //            .link(Link.fromPath("/").build())
        //            .build()
        //    );
        //}
    }
    
}