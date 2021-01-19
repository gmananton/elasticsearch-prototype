package com.gman.index;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gman.domain.Article;
import com.gman.repository.BlogRepository;
import com.gman.util.FileHelper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * Created by Anton Mikhaylov on 23.12.2020.
 */
@Component
@Slf4j
public class IndexInitializer {

    @Qualifier("strict_match")
    @Autowired
    private  BlogRepository repository;

    @Autowired
    private  ObjectMapper mapper;

    private static final String INDEX_NAME = "blog";
    private static final String TYPE_NAME = "post";

    @PostConstruct
    @SneakyThrows
    public void init() {
        deleteIndex();
        mapper.readValue(FileHelper.loadResource("/news.json"), new TypeReference<List<Article>>() {
        }).stream().forEach(repository::save);
    }

    @PreDestroy
    public void tearDown() {
        deleteIndex();
    }

    private void deleteIndex() {
        try {
            repository.deleteAll();
        } catch (Exception e) {

        }
    }


}
