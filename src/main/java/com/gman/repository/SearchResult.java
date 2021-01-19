package com.gman.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gman.domain.Article;
import lombok.Data;

import java.util.List;

/**
 * Created by Anton Mikhaylov on 12.01.2021.
 */
@Data
public class SearchResult {

    private HitResult hits;

    @Data
    public static class HitResult {
        private List<Record> hits;
    }


    @Data
    public static class Record {

        @JsonProperty("_index")
        private String index;

        @JsonProperty("_type")
        private String type;

        @JsonProperty("_id")
        private String id;

        @JsonProperty("_score")
        private double score;

        @JsonProperty("_source")
        private Article post;
    }
}
