package com.news.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity (name= "Groups")
@NoArgsConstructor
@Getter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "group_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "division_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Division division;

    private String groupName;
}
