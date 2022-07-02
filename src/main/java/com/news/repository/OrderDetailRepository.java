package com.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

}
