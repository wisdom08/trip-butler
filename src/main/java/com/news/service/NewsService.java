package com.news.service;

import com.news.dto.NewsDto;
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
    public List<NewsDto> searchNews(String keyword) {
        List<News> newsList = newsRepository.findAllByKeywordContainingOrderByDateDesc(keyword);
        List <NewsDto> newsDtoList = new ArrayList<>();
        for (News news : newsList){
            newsDtoList.add(NewsDto.builder()
                    .news(news)
                    .build());
        }
        return newsDtoList;
    }
}
