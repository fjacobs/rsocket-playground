package com.example.requester.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties("spring.rsocket.server")
@Validated
public class ServerConfigProperties {

    @NotNull
    private String hostAddress;
    @NotNull
    private int port;
    @NotNull
    private String transport;
    @NotNull
    private String mappingPath;

    public ServerConfigProperties() { }

    public ServerConfigProperties(String hostAddress, int port, String transport, String mappingPath) {
        this.hostAddress = hostAddress;
        this.port = port;
        this.transport = transport;
        this.mappingPath = mappingPath;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public int getPort() {
        return port;
    }

    public String getMappingPath() {
        return mappingPath;
    }

    public String getTransport() {
        return transport;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public void setMappingPath(String mappingPath) {
        this.mappingPath = mappingPath;
    }
}