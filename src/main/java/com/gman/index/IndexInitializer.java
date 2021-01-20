package com.gman.index;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gman.domain.Article;
import com.gman.repository.BlogRepository;
import com.gman.util.FileHelper;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton Mikhaylov on 23.12.2020.
 */
@Component
@Slf4j
public class IndexInitializer {

    @Autowired
    private Map<String, BlogRepository> repos;

    @Autowired
    private  ObjectMapper mapper;

    @PostConstruct
    @SneakyThrows
    public void init() {
        deleteIndex();
        mapper.readValue(FileHelper.loadResource("/news.json"), new TypeReference<List<Article>>() {
        }).stream().forEach(getAnyRepo()::save);
    }

    @PreDestroy
    public void tearDown() {
        deleteIndex();
    }

    private void deleteIndex() {
        try {
            getAnyRepo().deleteAll();
        } catch (Exception e) {

        }
    }

    private BlogRepository getAnyRepo() {
        return repos.get(repos.keySet().stream().findAny().get());
    }


}
