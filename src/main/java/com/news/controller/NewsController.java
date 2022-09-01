package com.news.controller;

import com.news.dto.NewsNoNormalMainResponseDto;
import com.news.service.NewsNoNormalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class NewsController {

    private final NewsNoNormalService newsNoNormalService;

    @GetMapping("/search")
    public List<NewsNoNormalMainResponseDto> getNewsList() {
        return newsNoNormalService.getNewsList();
    }
}
