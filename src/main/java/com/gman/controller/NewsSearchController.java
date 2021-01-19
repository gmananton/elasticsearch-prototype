package com.gman.controller;

import com.gman.domain.Article;
import com.gman.repository.BlogRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * Created by Anton Mikhaylov on 30.12.2020.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class NewsSearchController {


//    private final SimpleBlogRepository repository;

    private final Map<String, BlogRepository> repos;

    @GetMapping("listNews")
    public List<News> getAll() {
        return repos.get(repos.keySet().stream().findAny().get()).getAll().stream().map(articleToNews).collect(toList());
    }


    @GetMapping("searchNews")
    public List<News> findNews(@RequestParam(name = "query", required = false) String query, @RequestParam String queryType) {
        return getRepo(queryType).find(query)
                .stream()
                .map(articleToNews)
                .collect(toList());
    }

    private BlogRepository getRepo(String queryType) {
        return repos.get(queryType);
    }


    private Function<Article, News> articleToNews = article -> new News(
            article.getTitle(),
            article.getBody(),
            new ArrayList<>() {{
                addAll(article.getTags());
            }});

    @Data
    @AllArgsConstructor
    public static class News {

        private String title;
        private String body;
        private List<String> tags;
    }


}
