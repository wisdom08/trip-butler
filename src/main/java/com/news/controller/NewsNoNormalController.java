package com.news.controller;

import com.news.service.NewsNoNormalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class NewsNoNormalController {

    private final NewsNoNormalService newsNoNormalService;

    @GetMapping("/resultpage")
    public String getNewsList() {
        return "resultpage";
    }
}
