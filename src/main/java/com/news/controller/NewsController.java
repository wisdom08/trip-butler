package com.news.controller;

import com.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/resultpage")
    public String getNewsList() {
        return "resultpage";
    }
}
