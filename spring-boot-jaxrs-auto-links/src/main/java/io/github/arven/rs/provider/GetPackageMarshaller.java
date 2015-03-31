package io.github.arven.rs.provider;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * We want to generate a Marshaller which will read its context from the JAXB
 * index files, rather than relying solely on the XmlSeeAlso annotation. This
 * is specific to the given project, and should define all of the packages
 * in which JAXB elements might be found.
 */
@Provider
public class GetPackageMarshaller implements ContextResolver<Marshaller> {

        private final Providers providers;

        public GetPackageMarshaller(@Context Providers providers) {
                this.providers = providers;
        }

        @Override
        public Marshaller getContext(Class<?> type) {
            try {
                JAXBContext context = JAXBContext.newInstance("io.github.arven.rs.services.example:io.github.arven.rs.hypertext");
                Marshaller marshaller = context.createMarshaller();
                return marshaller;
            } catch (JAXBException je) {
                return null;
            }
        }
        
}