package com.example.requester.configuration;


import io.rsocket.transport.ClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import reactor.core.publisher.Mono;

@Configuration
public class RSocketRequesterConfig {

    @Bean(name="CustomRequester")
    public Mono<RSocketRequester> rSocketRequester(ClientTransport clientTransport, RSocketStrategies strategies) {
        return RSocketRequester.builder()
                .rsocketStrategies(strategies)
                .connect(clientTransport);
    }


}
