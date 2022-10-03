package com.news.service;

import com.news.dto.PostResponseDto;
import com.news.entity.Post;
import com.news.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(String newsId, String contents, String writer, String url, String title) {
        Post post = Post.createPost(newsId, contents, writer, url, title);
        postRepository.save(post);
    }

    public List<PostResponseDto> getMyPosts() {
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        return posts.stream().map(PostResponseDto::from).collect(Collectors.toList());
    }
}
