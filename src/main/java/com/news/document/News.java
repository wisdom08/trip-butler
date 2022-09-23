package com.news.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Getter
@Document(indexName = "news*")
public class News {
    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String section;
    private String press;
    private String author;
    private String title;
    private String contents;
    private String imageUrl;
    private String url;
}
