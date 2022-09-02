package com.news.repository;

import com.news.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News>  findAllByKeywordContainingOrderByDateDesc(String keyword);
}
