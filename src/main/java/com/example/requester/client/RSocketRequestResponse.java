package com.example.requester.client;

import com.example.requester.configuration.ServerConfigProperties;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

import static com.example.requester.server.RsocketController.*;

@Profile("client")
@Service
public class RSocketRequestResponse implements ApplicationListener<ContextRefreshedEvent> {

    private final Mono<RSocketRequester> requester;
    private ServerConfigProperties serverConfigProp;

    @Autowired
    public RSocketRequestResponse(Mono<RSocketRequester> requester, ServerConfigProperties serverConfigProp) {
        this.serverConfigProp = serverConfigProp;
        this.requester = requester;
        System.out.println("BootService constructor");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        sendFcRespReq(requester).block();
    }

    public Mono<String> sendStringRequest(Mono<RSocketRequester> rsocketRequester) {
        return rsocketRequester.flatMap(req -> req.route(REQUEST_RESPONSE_STRING)
                .retrieveMono(String.class))
                .doOnNext(System.out::println);
    }

    public Mono<FeatureCollection> sendFcRespReq(Mono<RSocketRequester> rsocketRequester) {

        return rsocketRequester.flatMap(req ->
                req.route(REQUEST_RESPONSE_JSON)
                        .retrieveMono(FeatureCollection.class))
                .doOnNext(fc -> {
                    System.out.println("Received FeatureCollection");
                    fc.getFeatures().forEach(feature -> System.out.println("Id:" + feature.getId()));
                });
    }

    public Flux<Feature> sendFcStreamReq(Mono<RSocketRequester> rsocketRequester) {

        return rsocketRequester
                .flatMapMany(req -> req.route(REQUEST_STREAM_JSON).retrieveFlux(Feature.class))
                .doOnNext(feature -> System.out.println(feature.getId()));
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("BootService initialized");
    }
}




//bug: wireshark only shows first feature after stream (multiple are send)