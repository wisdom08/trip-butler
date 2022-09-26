package com.news.repository;

import com.news.entity.RefreshToken;
import com.news.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByRefreshTokenValue(String refreshToken);

    Optional<RefreshToken> findByUser(User user);

    void deleteByUser(User user);
}
