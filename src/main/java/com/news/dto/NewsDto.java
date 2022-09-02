package com.news.dto;

import com.news.model.News;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NewsDto {
    private Long id;
    private LocalDateTime date;
    private String press;
    private String author;
    private String title;
    private String contents;
    private String url;

    @Builder
    public NewsDto(News news){
        this.id = news.getId();
        this.date = news.getDate();
        this.press = news.getPress();
        this.author = news.getAuthor();
        this.title = news.getTitle();
        this.contents = news.getContents();
        this.url = news.getUrl();
    }
}
