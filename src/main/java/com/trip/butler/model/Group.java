package com.trip.butler.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "group_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Division division;

    private String groupName;
}
