package com.news.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.document.News;
import com.news.dto.SearchRequestDto;
import com.news.dto.util.SearchUtil;
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
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final RestHighLevelClient client;

    public List<News> search(SearchRequestDto dto) {
        String[] sections = dto.getSection();
        StringBuilder stringBuilder = new StringBuilder();
        for (String index : sections) {
            stringBuilder.append("newsv2-" + index + ",");
        }
        String indexName = stringBuilder.toString();

        SearchRequest request = SearchUtil.buildSearchRequest(indexName, dto);
        return searchInternal(request);
    }


    public List<News> searchInternal(SearchRequest request) {
        if (request == null) {
            log.error("Failed to build search request");
            return Collections.emptyList();
        }

        try {
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            System.out.println(response.getHits().getTotalHits());


            final SearchHit[] searchHits = response.getHits().getHits();
            final List<News> newsList = new ArrayList<>(searchHits.length);
            HashMap<String, Object> hashMap = new HashMap<>();
            for (SearchHit hit : searchHits) {
                hashMap.put("id", hit.getId());
                hashMap.put("date", hit.getSourceAsMap().get("date"));
                hashMap.put("section", hit.getSourceAsMap().get("section"));
                hashMap.put("press", hit.getSourceAsMap().get("press"));
                hashMap.put("author", hit.getSourceAsMap().get("author"));
                hashMap.put("title", hit.getSourceAsMap().get("title"));
                hashMap.put("url", hit.getSourceAsMap().get("url"));
                hashMap.put("imageUrl", hit.getSourceAsMap().get("imageUrl"));
                hashMap.put("contents", hit.getSourceAsMap().get("contents"));

                newsList.add(
                        MAPPER.readValue(new ObjectMapper().writeValueAsString(hashMap), News.class)
                );
            }
            return newsList;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
