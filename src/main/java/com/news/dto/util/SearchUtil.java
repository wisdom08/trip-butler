package com.news.dto.util;

import com.news.dto.SearchRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchUtil {

    //send a search request to elasticsearch
    public static SearchRequest buildSearchRequest(String indexName, SearchRequestDto dto) {
        try {
            String[] mustNotList = getList(dto);
            ArrayList<String> mustNot = new ArrayList<>(Arrays.asList(mustNotList));
            QueryBuilder searchQuery = getQueryBuilder(dto);
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

            if (dto.getStartDate() == "" && mustNotList.length == 7) {
                boolQueryBuilder
                        .filter(QueryBuilders.termsQuery("section", dto.getSection()))
                        .filter(QueryBuilders.rangeQuery("date").lte(dto.getEndDate()))
                        .should(searchQuery);
            }

            if (dto.getStartDate() == "" && mustNotList.length != 7) {
                if (mustNot.contains("기타")) {
                    boolQueryBuilder = QueryBuilders.boolQuery()
                            .filter(QueryBuilders.termsQuery("section", dto.getSection()))
                            .filter(QueryBuilders.rangeQuery("date").lte(dto.getEndDate()))
                            .filter(QueryBuilders.termsQuery("press", dto.getPress()))
                            .should(searchQuery);
                } else {
                    boolQueryBuilder = QueryBuilders.boolQuery()
                            .filter(QueryBuilders.termsQuery("section", dto.getSection()))
                            .filter(QueryBuilders.rangeQuery("date").lte(dto.getEndDate()))
                            .mustNot(QueryBuilders.termsQuery("press", mustNotList))
                            .should(searchQuery);
                }
            }

            if (dto.getStartDate() != "" && mustNotList.length == 7) {
                boolQueryBuilder = QueryBuilders.boolQuery()
                        .filter(QueryBuilders.termsQuery("section", dto.getSection()))
                        .filter(QueryBuilders.rangeQuery("date").gte(dto.getStartDate()).lte(dto.getEndDate()))
                        .should(searchQuery);
            }

            if (dto.getStartDate() != "" && mustNotList.length != 7) {
                if (mustNot.contains("기타")) {
                    boolQueryBuilder = QueryBuilders.boolQuery()
                            .filter(QueryBuilders.termsQuery("section", dto.getSection()))
                            .filter(QueryBuilders.rangeQuery("date").gte(dto.getStartDate()).lte(dto.getEndDate()))
                            .filter(QueryBuilders.termsQuery("press", dto.getPress()))
                            .should(searchQuery);
                } else {
                    boolQueryBuilder = QueryBuilders.boolQuery()
                            .filter(QueryBuilders.termsQuery("section", dto.getSection()))
                            .filter(QueryBuilders.rangeQuery("date").gte(dto.getStartDate()).lte(dto.getEndDate()))
                            .mustNot(QueryBuilders.termsQuery("press", mustNotList))
                            .should(searchQuery);
                }
            }

            SearchSourceBuilder builder = new SearchSourceBuilder()
                    .from(0)
                    .size(100)
                    .trackTotalHits(true)
                    .query(boolQueryBuilder);

            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);

            return request;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] getList(final SearchRequestDto dto) {
        ArrayList<String> pressNames = new ArrayList<>(Arrays.asList(dto.getPress()));
        String[] pressList = {"중앙일보", "한국일보", "동아일보", "조선일보", "한겨레", "매일경제", "기타"};
        ArrayList<String> press = new ArrayList<>(Arrays.asList(pressList));
        if (pressList.length == dto.getPress().length) {
            return dto.getPress();
        } else {
            press.removeAll(pressNames);
            return press.toArray(new String[press.size()]);
        }
    }

    //제목,내용의 query로 querybuilder 생성
    public static QueryBuilder getQueryBuilder(final SearchRequestDto dto) {
        //check if the dto is null
        if (dto == null) {
            return null;
        }
        //if not, create a query builder
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm(), "search_*")
                .operator(Operator.AND)
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
        return queryBuilder;
    }
}
