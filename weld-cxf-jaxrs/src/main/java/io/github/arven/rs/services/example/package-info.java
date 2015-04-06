/**
 * This is an example Hypertext Application which supports CRUD operations and
 * is more integrated than previous examples. The application looks like a
 * simple clone of a web application like Twitter, but it is just a mockup and
 * is designed solely to show the integration of the component parts:
 * 
 *      - JPA
 *      - JTA
 *      - CDI
 *      - Hypertext
 */
@XmlSchema( namespace="http://github.com/Arven/java-rest-examples", elementFormDefault=XmlNsForm.QUALIFIED , xmlns = {@XmlNs(namespaceURI = "http://github.com/Arven/java-rest-examples", prefix="api")})
package io.github.arven.rs.services.example;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;