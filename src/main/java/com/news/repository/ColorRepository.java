package com.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.entity.Color;

public interface ColorRepository extends JpaRepository<Color, Integer> {

}
