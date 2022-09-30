package com.news.service;

import com.news.entity.Post;
import com.news.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(String newsId, String contents) {
        Post post = Post.createPost(newsId, contents);
        postRepository.save(post);
    }
}
