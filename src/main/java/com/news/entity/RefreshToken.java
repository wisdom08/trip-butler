package com.news.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken extends Timestamped {

    @Id
    @Column(nullable = false)
    private Long id;

    @JoinColumn(name = "userId", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String refreshTokenValue;

    @Builder
    public RefreshToken(Long id, User user, String refreshTokenValue) {
        this.id = id;
        this.user = user;
        this.refreshTokenValue = refreshTokenValue;
    }
}