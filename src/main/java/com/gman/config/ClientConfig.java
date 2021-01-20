package com.gman.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by Anton Mikhaylov on 19.01.2021.
 */
@Configuration
public class ClientConfig {

    @Bean
    public RestClient elasticClient(Environment environment) {
        ElasticServerProps props = Binder.get(environment)
                .bind("elastic", ElasticServerProps.class)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect or abscent properties with prefix 'elastic'"));
        return RestClient.builder(new HttpHost(props.getHost(), props.getPort(), "http")).build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * Created by Anton Mikhaylov on 20.01.2021.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ElasticServerProps {

        private String host;
        private int port;
    }
}