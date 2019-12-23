package com.example.requester.server;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Profile("server")
@Controller
public class RsocketController {

    public final static String REQUEST_RESPONSE_STRING = "REQUEST_RESPONSE_STRING";
    public final static String REQUEST_RESPONSE_JSON = "REQUEST_RESPONSE_JSON";
    public final static String REQUEST_STREAM_JSON = "REQUEST_STREAM_JSON";

    FeatureCollection features = new FeatureCollection();

    public RsocketController() {

        Feature feature = new Feature();
        feature.setId("thisisanid1");
        feature.setProperty("propertyName", 11111);

        Feature feature2 = new Feature();
        feature2.setId("thisisanid2");
        feature2.setProperty("propertyName", 22222);

        features.setFeatures(Arrays.asList(feature, feature2));
    }

    @Profile(REQUEST_RESPONSE_STRING)
    @CrossOrigin
    @MessageMapping(REQUEST_RESPONSE_STRING)
    public Mono<String> getString() {
        return Mono.just("server->hello");
    }

    @Profile(REQUEST_RESPONSE_JSON)
    @CrossOrigin
    @MessageMapping(REQUEST_RESPONSE_JSON)
    public Mono<FeatureCollection> getFeatureCollection() {
        return Mono.just(features);
    }

    @Profile(REQUEST_STREAM_JSON)
    @CrossOrigin
    @MessageMapping(REQUEST_STREAM_JSON)
    public Flux<Feature> streamFeatures(RSocketRequester rSocketRequest) {
        return Flux.fromIterable(features);
    }

}
