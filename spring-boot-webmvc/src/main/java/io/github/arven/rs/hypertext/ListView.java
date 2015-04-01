package io.github.arven.rs.hypertext;

import io.github.arven.rs.services.example.Group;
import io.github.arven.rs.services.example.Message;
import io.github.arven.rs.services.example.Person;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * The ListView is a simple wrapper over the Hyper datatype, which should
 * be used to provide limits, list reversal, list offset, and list size.
 * Any traditional list can be used and will be returned inside the Hyper
 * wrapper, but the ListView allows for a much finer grained control over
 * the list view by the user as well as by the application.
 * 
 * @author Brian Becker
 * @param  <DataType>
 */
@XmlRootElement(name = "list")
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({Group.class, Message.class, Person.class})
public class ListView<DataType> {
    
    private static int DEFAULT_LIST_LIMIT = 100;
    
    private List<DataType> list;
    private Integer offset = 0;
    private Integer limit = DEFAULT_LIST_LIMIT;
    private Boolean reverse = false;
    
    public ListView() {}
           
    /**
     * Create a new List message with the verbatim list contents, from any
     * valid Java collection. The order of the collection items will be
     * maintained, if the collection supports order. The duplicity of the
     * collection items will also be maintained, if the collection supports
     * duplicates. However, the list will be returned as-is, without a
     * subset or slice.
     * 
     * @param   collection  The collection of elements for the list
     */
    public ListView(List<DataType> collection) {
        this.list = collection;
    }
    
    /**
     * Get the collection of this list view. This is the list which actually
     * backs the view, and it must be a list in order to allow the ability to
     * provide offsets and limits on the list.
     * 
     * @return The list, in entirety
     */
    public List<DataType> collection() {
        return this.list;
    }
    
    /**
     * Set the offset for this list view. This is the number of elements which
     * are skipped, after reversal, of the list.
     * 
     * @param offset the offset
     * @return the list view
     */
    public ListView offset(Integer offset) {
        this.offset = offset;
        return this;
    }
    
    /**
     * Get the offset for the list view. This is the number of elements which
     * are skipped, after reversal, of the list. This offset may be null,
     * which represents the lack of any configuration of an offset.
     * 
     * @return the offset, or null for no offset configuration
     */
    public Integer offset() {
        return this.offset;
    }
    
    /**
     * Set the limit for the list view. This is the number of elements which
     * will be allowed to be displayed, after which the rest will not be
     * shown. This limit defaults to 100, showing the 100 most relevant
     * items in a list.
     * 
     * @param limit the limit for the list view
     * @return the list view
     */
    public ListView limit(Integer limit) {
        this.limit = limit;
        return this;
    }
    
    /**
     * Get the limit for the list view. This is the number of elements which
     * will be allowed to be displayed, after which the rest will not be
     * shown. This limit defaults to 100, showing the 100 most relevant
     * items in a list. If the limit is null, the shown items should not
     * be limited.
     * 
     * @return the limit for the list view, or null for no limit
     */
    public Integer limit() {
        return this.limit;
    }    
    
    /**
     * Set the reversal of the list view. If this value is true, then the
     * list will be reversed in whatever order it is passed in. If the value
     * is false, the order will not be reversed. The purpose of this method
     * is both to reverse the list and mark the list as reversed so it is
     * displayed in a relevant location.
     * 
     * @param reverse whether or not the list should be reversed
     * @return the list view
     */
    public ListView reverse(Boolean reverse) {
        this.reverse = reverse;
        return this;
    }  
    
    /**
     * Get the reversal of the list view. If this value is true, then the
     * list should be reversed. If the value is false, the list should not
     * be reversed. The purpose of this method is to determine if the list
     * needs to be reversed in order to be put into a certain serialization.
     * For instance, an XML list should have an attribute marking the list
     * as reversed as well as the list itself being reversed.
     * 
     * @return whether or not the list should be reversed
     */
    public Boolean reverse() {
        return this.reverse;
    }        
    
}