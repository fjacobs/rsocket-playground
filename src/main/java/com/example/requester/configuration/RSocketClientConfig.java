package com.example.requester.configuration;

import io.rsocket.RSocketFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


//Responder configuration
@Profile("client")
@Configuration
public class RSocketClientConfig {

    public RSocketClientConfig() {
    }

//    The data and metadata mime types cannot be set directly on the ClientRSocketFactory and will be overridden.
//    Use the shortcuts dataMimeType(MimeType) and metadataMimeType(MimeType) on this builder instead.
//    The frame decoder also cannot be set directly and instead is set to match the configured DataBufferFactory.
//    For the setupPayload, consider using methods on this builder to specify the route, other metadata, and data as Object values to be encoded.
//    To configure client side responding, see RSocketMessageHandler.clientResponder(RSocketStrategies, Object...).

    public void factoryTest() {
        RSocketFactory.ClientRSocketFactory x = new RSocketFactory.ClientRSocketFactory();
    }

}
