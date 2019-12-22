package com.example.requester.configuration;

import io.rsocket.RSocketFactory;
import io.rsocket.transport.ClientTransport;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.messaging.rsocket.RSocketStrategies;

import java.net.URI;

@Profile("proper")
@Configuration
public class RSocketClientConfig {

    String rsocketHost;
    int serverPort;
    String mappingPath;

    public RSocketClientConfig() {
    }

    @Profile("tcp")
    @Bean(value="tcptransport")
    public ClientTransport tcpTransport(ServerConfigProperties serverProp) {

        System.err.println(serverProp.getHostAddress());

        return TcpClientTransport.create(serverProp.getHostAddress(), serverProp.getPort());
    }

    @Profile("websocket")
    @Bean(value="websockettransport")
    public ClientTransport websocketTransport(ServerConfigProperties serverProp) {
        return WebsocketClientTransport.create(URI.create("ws://localhost:" + serverProp.getPort() + serverProp.getMappingPath()));
    }

//    The data and metadata mime types cannot be set directly on the ClientRSocketFactory and will be overridden.
//    Use the shortcuts dataMimeType(MimeType) and metadataMimeType(MimeType) on this builder instead.
//    The frame decoder also cannot be set directly and instead is set to match the configured DataBufferFactory.
//    For the setupPayload, consider using methods on this builder to specify the route, other metadata, and data as Object values to be encoded.
//    To configure client side responding, see RSocketMessageHandler.clientResponder(RSocketStrategies, Object...).

    public void factoryTest() {
        RSocketFactory.ClientRSocketFactory x = new RSocketFactory.ClientRSocketFactory();
    }

    @Bean
    public RSocketStrategies strategies() {

        return RSocketStrategies.builder()
                .encoders(encoders -> encoders.add(new Jackson2CborEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
                .build();
    }

}
