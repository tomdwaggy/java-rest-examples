/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.provider;


import java.lang.annotation.Annotation;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.jaxrs.base.ProviderBase;
import com.fasterxml.jackson.jaxrs.cfg.Annotations;
import com.fasterxml.jackson.jaxrs.cfg.EndpointConfigBase;
import com.fasterxml.jackson.jaxrs.cfg.MapperConfiguratorBase;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import io.github.arven.rs.provider.JacksonJaxbYamlProvider.YamlEndpointConfig;
import io.github.arven.rs.provider.JacksonJaxbYamlProvider.YamlMapperConfigurator;
import java.util.ArrayList;
//import YamlEndpointConfig;
//import YamlMapperConfigurator;

/**
 *
 * @author bvan
 */
@Provider
@Consumes({"application/yaml", "text/yaml"})
@Produces({"application/yaml", "text/yaml"})
public class JacksonJaxbYamlProvider
        extends ProviderBase<JacksonJaxbYamlProvider, ObjectMapper, YamlEndpointConfig, YamlMapperConfigurator> {

    public static class YamlMapperConfigurator
            extends MapperConfiguratorBase<YamlMapperConfigurator, ObjectMapper> {

        public YamlMapperConfigurator(ObjectMapper mapper, Annotations[] defAnnotations){
            super(mapper, defAnnotations);
        }

        /**
         * Method that locates, configures and returns {@link ObjectMapper} to use
         */
        @Override
        public synchronized ObjectMapper getConfiguredMapper(){
            /* important: should NOT call mapper(); needs to return null
             * if no instance has been passed or constructed
             */
            return _mapper;
        }

        @Override
        public synchronized ObjectMapper getDefaultMapper(){
            if(_defaultMapper == null){
                _defaultMapper = new ObjectMapper(new YAMLFactory());
                _defaultMapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector(_defaultMapper.getTypeFactory()));
                //_setAnnotations(_defaultMapper, _defaultAnnotationsToUse);
            }
            return _defaultMapper;
        }


        @Override
        protected ObjectMapper mapper(){
            if(_mapper == null){
                _mapper = new ObjectMapper(new YAMLFactory());
                _mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector(_defaultMapper.getTypeFactory()));
                //_setAnnotations(_mapper, _defaultAnnotationsToUse);
            }
            return _mapper;
        }

        @Override
        protected AnnotationIntrospector _resolveIntrospectors(Annotations[] annotationsToUse){
            // Let's ensure there are no dups there first, filter out nulls
            ArrayList<AnnotationIntrospector> intr = new ArrayList<>();
            for(Annotations a: annotationsToUse){
                if(a != null){
                    intr.add(_resolveIntrospector(a));
                }
            }
            int count = intr.size();
            if(count == 0){
                return AnnotationIntrospector.nopInstance();
            }
            AnnotationIntrospector curr = intr.get(0);
            for(int i = 1, len = intr.size(); i < len; ++i){
                curr = AnnotationIntrospector.pair(curr, intr.get(i));
            }
            return curr;
        }

        protected AnnotationIntrospector _resolveIntrospector(Annotations ann){
            switch(ann){
                case JACKSON:
                    return new JacksonAnnotationIntrospector();
                default:
                    throw new IllegalStateException();
            }
        }
    }

    public static class YamlEndpointConfig
            extends EndpointConfigBase<YamlEndpointConfig> {

        protected YamlEndpointConfig(){
        }

        public static YamlEndpointConfig forReading(ObjectReader reader,
                Annotation[] annotations){
            return new YamlEndpointConfig()
                    .add(annotations, false)
                    .initReader(reader);
        }

        public static YamlEndpointConfig forWriting(ObjectWriter writer,
                Annotation[] annotations){
            YamlEndpointConfig config = new YamlEndpointConfig();
            return config
                    .add(annotations, true)
                    .initWriter(writer);
        }

        @Override
        public Object modifyBeforeWrite(Object value){
            // nothing to add
            return value;
        }
    }

    public final static Annotations[] BASIC_ANNOTATIONS = {
        Annotations.JACKSON
    };

    @Context
    protected Providers _providers;


    public JacksonJaxbYamlProvider(){
        this(null, BASIC_ANNOTATIONS);
    }

    public JacksonJaxbYamlProvider(Annotations... annotationsToUse){
        this(null, annotationsToUse);
    }

    public JacksonJaxbYamlProvider(ObjectMapper mapper){
        this(mapper, BASIC_ANNOTATIONS);
    }

    public JacksonJaxbYamlProvider(ObjectMapper mapper, Annotations[] annotationsToUse){
        super(new YamlMapperConfigurator(mapper, annotationsToUse));
    }

    @Override
    public Version version(){
        return PackageVersion.VERSION;
    }


    @Override
    protected boolean hasMatchingMediaType(MediaType mediaType){
        if(mediaType != null){
            String subtype = mediaType.getSubtype();
            return "yaml".equalsIgnoreCase(subtype) || subtype.endsWith("+yaml");
        }
        return false;
    }

    @Override
    protected ObjectMapper _locateMapperViaProvider(Class<?> type, MediaType mediaType){
        if(_providers != null){
            ContextResolver<ObjectMapper> resolver = _providers.
                    getContextResolver(ObjectMapper.class, mediaType);
            if(resolver == null){
                resolver = _providers.getContextResolver(ObjectMapper.class, null);
            }
            if(resolver != null){
                return resolver.getContext(type);
            }
        }
        return null;
    }

    @Override
    protected YamlEndpointConfig _configForReading(ObjectReader reader,
            Annotation[] annotations){
        return YamlEndpointConfig.forReading(reader, annotations);
    }

    @Override
    protected YamlEndpointConfig _configForWriting(ObjectWriter writer,
            Annotation[] annotations){
        return YamlEndpointConfig.forWriting(writer, annotations);
    }

}