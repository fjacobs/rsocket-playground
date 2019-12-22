package com.example.requester;

import com.example.requester.configuration.ServerConfigProperties;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;

@Profile("proper")
@Service
public class BootService implements ApplicationListener<ContextRefreshedEvent> {

    private final Mono<RSocketRequester> requester;
    private ServerConfigProperties serverConfigProp;

    @Autowired
//    public BootService(@Qualifier("CustomRequester") Mono<RSocketRequester> requester) {
    public BootService(Mono<RSocketRequester> requester, ServerConfigProperties serverConfigProp) {
        this.serverConfigProp = serverConfigProp;
        this.requester = requester;
        System.out.println("BootService constructor");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        sendFcRespReq(requester).block();
    }

    public Mono<String> sendStringRequest(Mono<RSocketRequester> rsocketRequester) {
        return rsocketRequester.flatMap(req -> req.route("route1")
                                                  .retrieveMono(String.class))
                                    .doOnNext(System.out::println);
    }

    public Mono<FeatureCollection> sendFcRespReq(Mono<RSocketRequester> rsocketRequester) {

        return rsocketRequester.flatMap(req ->
                req.route("route2")
                   .retrieveMono(FeatureCollection.class))
                   .doOnNext(fc-> System.out.println(fc.getFeatures().get(0).getId()));
    }

    public Flux<Feature> sendFcStreamReq(Mono<RSocketRequester> rsocketRequester) {

        return rsocketRequester
                .flatMapMany(req->req.route("route3").retrieveFlux(Feature.class))
                .doOnNext(feature -> System.out.println(feature.getId()));
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("BootService initialized");
    }
}




//bug: wireshark only shows first feature after stream (multiple are send)