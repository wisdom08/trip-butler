package com.news.service;


import com.news.dto.NewsNoNormalMainResponseDto;
import com.news.model.NewsNoNormal;
import com.news.repository.NewsNoNormalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsNoNormalService {

    private final NewsNoNormalRepository newsNoNormalRepository;

    // 뉴스 전체 리스트 조회
    public List<NewsNoNormalMainResponseDto> getNewsList() {
        List<NewsNoNormal> findNews = newsNoNormalRepository.findAllByOrderByDateDesc();
        List<NewsNoNormalMainResponseDto> newsList = new ArrayList<>();
        for(NewsNoNormal newsNoNormal : findNews) {
            NewsNoNormalMainResponseDto newsNoNormalMainResponseDto = NewsNoNormalMainResponseDto.builder()
                    .newsNoNormal(newsNoNormal)
                    .build();
            newsList.add(newsNoNormalMainResponseDto);
        }
        return newsList;
    }

}
