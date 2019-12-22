package com.example.requester;

import io.rsocket.RSocket;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

@Controller
public class MyController {

    FeatureCollection features = new FeatureCollection();

    public MyController() {
        Feature feature = new Feature();
        feature.setId("thisisanid1");
        feature.setProperty("propertyName", 11111);

        Feature feature2 = new Feature();
        feature2.setId("thisisanid2");
        feature2.setProperty("propertyName", 22222);

        features.setFeatures(Arrays.asList(feature,feature2));
        System.out.println("Started controller.....");
    }

    @CrossOrigin
    @MessageMapping("route1")
    public Mono<String> getString() {
        return Mono.just("server->hello");
    }

    @CrossOrigin
    @MessageMapping("route2")
    public Mono<FeatureCollection> getFeatureCollection() {
        return Mono.just(features);
    }

    @CrossOrigin
    @MessageMapping("route3")
    public Flux<Feature> streamFeatures(RSocketRequester rSocketRequest) {
        return Flux.fromIterable(features);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("MyController initialized");
    }
}
