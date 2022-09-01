package com.news.controller;

import com.news.dto.NewsResponseDto;
import com.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MainController {

    private final NewsService newsService;

    @GetMapping("/search")
    public List<NewsResponseDto> getNewsList() {
        return newsService.getNewsList();
    }
}
