package com.news.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Post extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String newsId;
    private String contents;

    protected Post() {}

    private Post(String newsId, String contents) {
        this.newsId = newsId;
        this.contents = contents;
    }

    public static Post createPost(String newsId, String contents) {
        return new Post(newsId, contents);
    }
}
