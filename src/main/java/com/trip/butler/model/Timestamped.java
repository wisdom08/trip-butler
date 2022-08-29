package com.trip.butler.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class Timestamped {

    @CreatedDate
    private LocalDateTime createdAt;

}
