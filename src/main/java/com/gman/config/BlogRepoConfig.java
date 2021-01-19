package com.gman.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gman.repository.BlogRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Anton Mikhaylov on 19.01.2021.
 */
@Configuration
@EnableConfigurationProperties(BlogRepoConfig.ElasticServerProps.class)
@RequiredArgsConstructor
public class BlogRepoConfig {

    private final ElasticServerProps props;

    @ConfigurationProperties(prefix = "elastic")
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ElasticServerProps {

        String host;
        int port;
    }

    @Bean("strict_match")
    public BlogRepository strictMatchRepo() {
        return new BlogRepository(elasticClient(), objectMapper()) {
            @Override
            protected String getQueryTemplateName() {
                return "strict_match";
            }
        };
    }

    @Bean("fuzzy_match")
    public BlogRepository fuzzyMatchRepo() {
        return new BlogRepository(elasticClient(), objectMapper()) {
            @Override
            protected String getQueryTemplateName() {
                return "fuzzy_match";
            }
        };
    }

    @Bean("prefix")
    public BlogRepository prefixRepo() {
        return new BlogRepository(elasticClient(), objectMapper()) {
            @Override
            protected String getQueryTemplateName() {
                return "prefix";
            }
        };
    }

    @Bean("multi_match")
    public BlogRepository multiMatchRepo() {
        return new BlogRepository(elasticClient(), objectMapper()) {
            @Override
            protected String getQueryTemplateName() {
                return "multi_match";
            }
        };
    }

    @Bean
    public RestClient elasticClient() {
        return RestClient.builder(new HttpHost(props.getHost(), props.getPort(), "http")).build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

}
