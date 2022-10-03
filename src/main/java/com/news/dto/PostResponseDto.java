package com.news.dto;

import com.news.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String url;
    private String writer;

    protected PostResponseDto() {};

    private PostResponseDto(Long id, String title, String contents, String url, String writer) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.writer = writer;
    }

    public static PostResponseDto from(Post entity) {
        return new PostResponseDto(entity.getId(), entity.getTitle(), entity.getContents(),
            entity.getUrl(), entity.getWriter());
    }
}
