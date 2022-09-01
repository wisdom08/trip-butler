package com.news.dto;

import com.news.model.News;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NewsResponseDto {

    private Long id;
    private LocalDateTime date;
    private String press;
    private String author;
    private String title;
    private String contents;
    private String url;
    private String keyword;
    private String location;
    private String organization;

    @Builder
    public NewsResponseDto(News news) {
        this.id = news.getId();
        this.date = news.getDate();
        this.press = news.getPress();
        this.author = news.getAuthor();
        this.title = news.getTitle();
        this.contents = news.getContents();
        this.url = news.getUrl();
        this.keyword = news.getKeyword();
        this.location = news.getLocation();;
        this.organization = news.getOrganization();
    }
}
