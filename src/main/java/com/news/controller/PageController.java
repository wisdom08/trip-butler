package com.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/resultpage")
    public String getPage(){
        return "resultpage";
    }
}
