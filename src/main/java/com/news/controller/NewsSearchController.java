package com.news.controller;

import com.news.document.News;
import com.news.dto.SearchRequestDto;
import com.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsSearchController {
    private final NewsService newsService;

    @PostMapping("/search")
    public List<News> search(@RequestBody SearchRequestDto dto) {
        return newsService.search(dto);
    }
}