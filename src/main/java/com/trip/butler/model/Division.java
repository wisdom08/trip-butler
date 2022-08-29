package com.trip.butler.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "division_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "section_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Section section;

    private String divisionName;
}
