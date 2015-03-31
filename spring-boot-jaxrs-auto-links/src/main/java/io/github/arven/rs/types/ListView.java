package io.github.arven.rs.types;

import io.github.arven.rs.services.example.Group;
import io.github.arven.rs.services.example.Message;
import io.github.arven.rs.services.example.Person;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * The DataList class provides a list message for JAXB. This works around
 * various issues where JAXRS duplicates the XML namespaces in each list
 * element. Furthermore, it also provides the list slice functionality
 * which can be specified by an offset and a limit of results to return,
 * which allows clients to access entire lists but prevents having to return
 * huge amounts of data.
 * 
 * @author Brian Becker
 * @param <DataType>
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
    
    public List<DataType> collection() {
        return this.list;
    }
    
    public ListView offset(Integer offset) {
        this.offset = offset;
        return this;
    }
    
    public Integer offset() {
        return this.offset;
    }
    
    public ListView limit(Integer limit) {
        this.limit = limit;
        return this;
    }
    
    public Integer limit() {
        return this.limit;
    }    
    
    public ListView reverse(Boolean reverse) {
        this.reverse = reverse;
        return this;
    }  
    
    public Boolean reverse() {
        return this.reverse;
    }        
    
}