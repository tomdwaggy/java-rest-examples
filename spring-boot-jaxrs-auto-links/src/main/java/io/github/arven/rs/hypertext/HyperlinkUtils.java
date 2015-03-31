package io.github.arven.rs.hypertext;

import autovalue.shaded.com.google.common.common.collect.ObjectArrays;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A general utility class for traversing and looking up hyperlink related
 * annotations.
 * 
 * @author Brian Becker
 */
public class HyperlinkUtils {
    
    public static Map<String, String> getHyperlinkValues(Object o) {
        Map map = new HashMap();
        for(Method m : ObjectArrays.concat(o.getClass().getDeclaredMethods(), o.getClass().getMethods(), Method.class)) {
            if(m.isAnnotationPresent(HyperlinkId.class)) {
                try {
                    HyperlinkId id = m.getAnnotation(HyperlinkId.class);
                    map.put(id.value(), m.invoke(o));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(HyperlinkUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        for(Field f : ObjectArrays.concat(o.getClass().getDeclaredFields(), o.getClass().getFields(), Field.class)) {
            if(f.isAnnotationPresent(HyperlinkId.class)) {
                try {
                    HyperlinkId id = f.getAnnotation(HyperlinkId.class);
                    boolean access = f.isAccessible();
                    f.setAccessible(true);
                    map.put(id.value(), f.get(o));
                    f.setAccessible(access);
                } catch (IllegalAccessException | IllegalArgumentException ex) {
                    Logger.getLogger(HyperlinkUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }        
        System.out.println(map);
        return map;
    }
    
}
