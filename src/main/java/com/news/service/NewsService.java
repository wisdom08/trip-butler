package com.news.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.document.News;
import com.news.dto.SearchRequestDto;
import com.news.dto.util.SearchUtil;
import com.news.helper.Indices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final RestHighLevelClient client;

    public List<News> search(SearchRequestDto dto) {
        final SearchRequest request = SearchUtil.buildSearchRequest(Indices.NEWS_INDEX, dto);

        return searchInternal(request);
    }


    public List<News> searchInternal(SearchRequest request) {
        if (request == null) {
            log.error("Failed to build search request");
            return Collections.emptyList();
        }

        try {
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            final SearchHit[] searchHits = response.getHits().getHits();
            final List<News> newsList = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                newsList.add(
                        MAPPER.readValue(hit.getSourceAsString(), News.class)
                );
            }

            return newsList;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
