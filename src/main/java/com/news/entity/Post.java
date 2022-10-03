package com.news.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Post extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String newsId;
    private String contents;
    private String url;
    private String title;
    private String writer;

    protected Post() {}

    private Post(String newsId, String contents, String writer, String url, String title) {
        this.newsId = newsId;
        this.contents = contents;
        this.writer = writer;
        this.url = url;
        this.title = title;
    }

    public static Post createPost(String newsId, String contents, String writer, String url, String title) {
        return new Post(newsId, contents, writer, url, title);
    }
}
