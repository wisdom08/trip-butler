package com.news.controller;

import com.news.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewPageController {

    private final PostService postService;
    public ViewPageController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("posts", postService.getMyPosts());
        return "index";
    }

    @GetMapping("/signup")
    public String getSignup() {
        return "signup";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
}
