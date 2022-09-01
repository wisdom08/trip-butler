package com.news.dto;

import com.news.model.NewsNoNormal;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NewsNoNormalMainResponseDto {

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
    public NewsNoNormalMainResponseDto(NewsNoNormal newsNoNormal) {
        this.id = newsNoNormal.getId();
        this.date = newsNoNormal.getDate();
        this.press = newsNoNormal.getPress();
        this.author = newsNoNormal.getAuthor();
        this.title = newsNoNormal.getTitle();
        this.contents = newsNoNormal.getContents();
        this.url = newsNoNormal.getUrl();
        this.keyword = newsNoNormal.getKeyword();
        this.location = newsNoNormal.getLocation();;
        this.organization = newsNoNormal.getOrganization();
    }
}
