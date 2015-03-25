package io.github.arven.rs.types;

import io.github.arven.rs.services.example.GroupData;
import io.github.arven.rs.services.example.MessageData;
import io.github.arven.rs.services.example.UserData;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * ReferenceList is a key based reference to a list of objects. It does not
 * use full objects, only single elements which provide a link to the objects
 * in a way that can be programmatically determined. This is useful for
 * database queries that return a large list of objects which will rarely
 * be iterated through, or where only the key of a query result actually
 * matters.
 * 
 * @author Brian Becker
 */
@XmlRootElement(name = "references")
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({GroupData.class, MessageData.class, UserData.class})
public class ReferenceList {

    @XmlElement         private List<String> id;
    @XmlAttribute   	private Integer offset;
    @XmlAttribute   	private Integer limit;
    @XmlAttribute   	private Integer size;
    @XmlAttribute   	private Boolean reverse;    
    
    public ReferenceList() {}
    
    /**
     * Create a new List message with the following parameters. The list
     * is simply the list which will be wrapped. The offset and span selects
     * the slice of the list which you want to return to the user. These
     * allow for the creation of a list which is always marshaled in the
     * same manner on all implementations as well as supporting common web
     * application frameworks.
     * 
     * @param   list        The list which will be wrapped
     * @param   offset      Offset into the wrapped list
     * @param   span        How many elements that should be returned
     * @param   reverse     Whether to reverse the list before slicing
     */
    public ReferenceList(List<String> list, Integer offset, Integer span, Boolean reverse) {
        this.offset = (offset == null || offset == 0) ? 0 : offset;
        offset = offset == null ? 0 : offset;
        
        if(list == null) {
            list = Collections.emptyList();
        }
        
        if(reverse) {
            this.reverse = true;
            list = Lists.reverse(list);
        }
        
        this.size = list.size();
        this.limit = offset + span < size ? span : 0;
        if(offset <= list.size()) {
            this.id = list.subList(offset, Math.min(offset + span, list.size()));
        } else {
            this.id = Collections.emptyList();
        }
    }    
    
    /**
     * Create a new DataReference with the given list of string identifiers.
     * This list should be some kind of unique reference to a series of
     * objects, that can be retrieved from a data store when more information
     * is required for them.
     * 
     * @param   id      The string identifier to use for the reference
     */
    public ReferenceList(List<String> id) { this.id = id; }
    
}