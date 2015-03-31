/**
 * This package contains the Hypertext Object Store annotations and helper
 * classes. These utilities are used for linking various data elements for
 * REST services as well as anything that needs a URI for global identity.
 * 
 * Here is a simple Hypertext definition.
 * 
 * @HyperlinkPath("/plain/text/pages/{id}")
 * public class PlainTextPage {
 * 
 *   @HyperlinkId
 *   private String id;
 *   private String value;
 * 
 *   public PlainTextPage(String id, String value) {
 *      this.id = id;
 *      this.value = value;
 *   }
 * 
 * }
 * 
 * A Hypertext definition is used to give a global identifier to any given
 * type of object. This is designed to be used to generate a unique name,
 * which is usually also a locator. However, it does not expose this object
 * at the locator's path.
 */
@XmlSchema( namespace="http://github.com/Arven/java-rest-examples/hypertext", elementFormDefault=XmlNsForm.QUALIFIED, xmlns = {@XmlNs(namespaceURI = "http://github.com/Arven/java-rest-examples/hypertext", prefix="hyper"), @XmlNs(namespaceURI = "http://github.com/Arven/java-rest-examples", prefix="api")} )
package io.github.arven.rs.hypertext;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;