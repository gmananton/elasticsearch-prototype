package com.gman.client;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by Anton Mikhaylov on 29.12.2020.
 */
@RequiredArgsConstructor
public class ElasticHttpClient<T> {

    private static final String BASE_URL = "http://localhost:9200/";

    @Delegate
    private final RestTemplate restTemplate;

    public void saveDoc(String indexName, String typeName, Object id, Object entity) {
        put(BASE_URL + "/" + indexName + "/" + typeName + "/" + id, entity);
    }

    public void saveDoc(String indexName, String typeName, Object entity) {
        postForEntity(BASE_URL + "/" + indexName + "/" + typeName, entity, Map.class);
    }

    public void deleteAll(String indexName) {
        delete(BASE_URL + "/" + indexName);
    }

    @Configuration
    public static class Config {

        @Bean
        public ElasticHttpClient blogPostClient() {
            return new ElasticHttpClient(new RestTemplate());
        }

    }
}
