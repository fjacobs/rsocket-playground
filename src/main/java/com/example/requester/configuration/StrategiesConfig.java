package com.example.requester.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketStrategies;

@Configuration
public class StrategiesConfig {

    @Profile("json")
    @Bean
    public RSocketStrategies jsonSerialization() {

        return RSocketStrategies.builder()
                .encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .build();
    }

    @Profile("cbor")
    @Bean
    public RSocketStrategies cborSerialization() {

        return RSocketStrategies.builder()
                .encoders(encoders -> encoders.add(new Jackson2CborEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
                .build();
    }


    @Profile("bytebuffer")
    @Bean
    public RSocketStrategies noSerialization() {

        return RSocketStrategies.builder()
                .build();
    }
}
