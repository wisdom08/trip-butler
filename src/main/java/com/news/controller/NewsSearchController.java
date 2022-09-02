package com.news.controller;

import com.news.dto.NewsDto;
import com.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsSearchController {
        private final NewsService newsService;

        @GetMapping("/search")
        public List<NewsDto> searchNewsKeyword(@RequestParam String keyword) {
            return newsService.searchNews(keyword);
        }
    }