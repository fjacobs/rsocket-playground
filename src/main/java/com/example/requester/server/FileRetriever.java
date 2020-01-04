package com.example.requester.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.FeatureCollection;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Profile("Server")
@Component
public class FileRetriever {

    public Mono<FeatureCollection> requestFeatures() throws JsonProcessingException {

        Mono<FeatureCollection> fc = Mono.just(new ObjectMapper().readValue(getString("green.geojson"), FeatureCollection.class));

        Assert.notNull(fc, "error reading file");

        return fc;
    }

    private String getString(String fileName) {
        String featureCollection = "";
        try {
            Path path = ResourceUtils.getFile(this.getClass().getResource("/data/" + fileName)).toPath();
            try {
                Charset charset = StandardCharsets.UTF_8;
                try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
                    String tempLine;
                    while ((tempLine = reader.readLine()) != null) {
                        featureCollection = "" + tempLine;
                    }
                } catch (IOException x) {
                    throw x;
                }
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception error) {
            error.printStackTrace();
            Assert.state(false, "Error reading geojson file '" + fileName + "'  in /resources: " + error.toString());
        }
        return featureCollection;
    }
}
