package com.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.entity.Supply;

public interface SupplyRepository extends JpaRepository<Supply, Integer> {

}
