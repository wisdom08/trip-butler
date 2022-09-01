package com.news.repository;

import com.news.model.NewsNoNormal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsNoNormalRepository extends JpaRepository<NewsNoNormal, Long> {

    List<NewsNoNormal> findAllByOrderByDateDesc();
}
