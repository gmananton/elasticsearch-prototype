package com.gman.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import com.gman.domain.Article;
import com.gman.util.FileHelper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Anton Mikhaylov on 12.01.2021.
 */
@RequiredArgsConstructor
@Slf4j
public abstract class BlogRepository {

    private static final String INDEX_URL = "/blog";
    private static final String SEARCH_URL = "/_search";


    private final RestClient elasticClient;

    private final ObjectMapper mapper;


    @SneakyThrows
    public void save(Article article) {
        Request req = new Request("POST", INDEX_URL + "/_doc");
        req.setJsonEntity(mapper.writeValueAsString(article));
        elasticClient.performRequest(req);
    }

    @SneakyThrows
    public void deleteAll() {
        Request req = new Request("DELETE", "/blog");
        elasticClient.performRequest(req);
    }

    @SneakyThrows
    public List<Article> getAll() {
        return performSearch("{}");
    }


    public List<Article> find(String searchString) {
        String searchQuery = FileHelper.loadResource("/searchTemplates/" + getQueryTemplateName() + ".json").replace("${searchString}", searchString);
        return performSearch(searchQuery);
    }

    protected abstract String getQueryTemplateName();

    @SneakyThrows
    private List<Article> performSearch(String query) {
        Request req = new Request("GET", INDEX_URL + SEARCH_URL);
        req.setJsonEntity(query);
        Response resp = elasticClient.performRequest(req);

        SearchResult searchResult = mapper.readValue(EntityUtils.toString(resp.getEntity()), SearchResult.class);

        List<Article> result = searchResult.getHits().getHits()
                .stream()
                .map(SearchResult.Record::getPost)
                .collect(Collectors.toList());

        return result;
    }
}
