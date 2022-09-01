package com.news.service;


import com.news.dto.NewsResponseDto;
import com.news.model.News;
import com.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    // 뉴스 전체 리스트 조회
    public List<NewsResponseDto> getNewsList() {
        List<News> findNews = newsRepository.findAllByOrderByDateDesc();
        List<NewsResponseDto> newsList = new ArrayList<>();
        for(News news : findNews) {
            NewsResponseDto newsResponseDto = NewsResponseDto.builder()
                    .news(news)
                    .build();
            newsList.add(newsResponseDto);
        }
        return newsList;
    }

}
