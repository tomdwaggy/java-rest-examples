package io.github.arven.rs.hypertext;

/**
 * This is a message which contains a Web Status Code, allowing for an
 * extensible set of message types. Before the message is sent, any message
 * with a WebStatusCode implementation will set the error code. This
 * allows for a response with an object type which can also return special
 * failure conditions.
 * 
 * @author Brian Becker
 */
public interface WebStatusCode {
    
    public abstract int error();
    
}
