package com.example.requester.configuration;

import io.rsocket.transport.ClientTransport;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URI;

@Configuration
public class TransportConfig {

    @Profile("tcp")
    @Bean(value = "tcptransport")
    public ClientTransport tcpTransport(ServerConfigProperties serverProp) {

        System.err.println(serverProp.getHostAddress());
        return TcpClientTransport.create(serverProp.getHostAddress(), serverProp.getPort());
    }

    @Profile("websocket")
    @Bean(value = "websockettransport")
    public ClientTransport websocketTransport(ServerConfigProperties serverProp) {
        return WebsocketClientTransport.create(URI.create("ws://localhost:" + serverProp.getPort() + serverProp.getMappingPath()));
    }
}
