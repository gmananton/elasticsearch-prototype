package com.gman.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import com.gman.repository.BlogRepository;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;

/**
 * Created by Anton Mikhaylov on 19.01.2021.
 */
@Configuration
public class BlogRepoConfig {

    @Bean
    public BlogRepoRegistrar beanDefinitionRegistrar(RestClient elasticClient, ObjectMapper mapper) {
        return new BlogRepoRegistrar(elasticClient, mapper);
    }

    @RequiredArgsConstructor
    public class BlogRepoRegistrar implements BeanDefinitionRegistryPostProcessor {

        private final RestClient elasticClient;
        private final ObjectMapper mapper;

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

            getQueryTemplateNames().forEach(tName -> {
                registry.registerBeanDefinition(tName, genericBeanDefinition(BlogRepository.class,
                        () -> new BlogRepository(elasticClient, mapper) {
                            @Override
                            protected String getQueryTemplateName() {
                                return tName;
                            }
                        }).getBeanDefinition());
            });

        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        }
    }

    @SneakyThrows
    private List<String> getQueryTemplateNames() {
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        return Arrays.stream(resolver.getResources("classpath*:searchTemplates/*.json"))
                .map(Resource::getFilename)
                .map(s -> s.substring(0, s.indexOf(".json")))
                .collect(toList());
    }

}