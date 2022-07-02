package com.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.entity.News;

public interface NewsRepository extends JpaRepository<News, Integer> {

}
