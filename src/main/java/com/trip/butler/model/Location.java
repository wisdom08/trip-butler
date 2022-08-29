package com.trip.butler.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "location_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    private String location;
}
