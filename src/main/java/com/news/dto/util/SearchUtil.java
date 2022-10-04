package com.news.dto.util;

import com.news.dto.SearchRequestDto;
import com.news.helper.Indices;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchUtil {

    //send a search request to elasticsearch
    public static SearchRequest buildSearchRequest(String indexName, SearchRequestDto dto) {
        try {

            QueryBuilder searchQuery = getQueryBuilder(dto);

            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                    .filter(QueryBuilders.termsQuery("section", dto.getSection()))
                    .filter(QueryBuilders.termsQuery("press", dto.getPress()))
                    .filter(QueryBuilders.rangeQuery("date").gte(dto.getStartDate()).lte(dto.getEndDate()))
                    .must(searchQuery);

            SearchSourceBuilder builder = new SearchSourceBuilder()
                    .from(0)
                    .size(100)
                    .trackTotalHits(true)
                    .postFilter(boolQueryBuilder);


            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);

            return request;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //제목,내용의 query로 querybuilder 생성
    public static QueryBuilder getQueryBuilder(final SearchRequestDto dto) {
        //check if the dto is null
        if (dto == null) {
            return null;
        }
        //if not, create a query builder
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm(), "title", "contents")
                .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                .operator(Operator.AND);

        return queryBuilder;
    }
}
