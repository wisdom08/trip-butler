package com.trip.butler.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class News extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "news_id")
    private Long id;

    private String press;
    private String author;
    private String title;
    private String contents;
    private String url;
}
