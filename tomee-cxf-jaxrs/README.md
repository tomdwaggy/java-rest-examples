# Spring Boot with Apache CXF

Apache CXF is an alternative REST provider, which has some schema generation support of its own. This is an attempt to demonstrate a more traditional Web application. The WADL generation support is configured so that the available methods to be called are all available in a single list.

Some clients can take advantage of the schema generation, such as SoapUI which can automatically populate a sample request. Linking would be a viable option as well.

## Swagger Support

Swagger is also enabled for this example REST service. It provides a schema which can be used with the Swagger UI web interface, which provides a description of all the services as well as a way to play around with the API.
