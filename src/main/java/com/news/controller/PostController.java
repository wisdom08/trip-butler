package com.news.controller;

import com.news.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/posts")
@Controller
public class PostController {

    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public String createPost(
        @RequestParam String newsId,
        @RequestParam String contents,
        @RequestParam String nickname,
        @RequestParam String url,
        @RequestParam String title) {
        postService.createPost(newsId, contents, nickname, url, title);
        return "redirect:/";
    }
}
