package com.news.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.news.entity.CartItem;
import com.news.entity.Order;
import com.news.entity.Product;

public interface OrderService {
	void order(Order order, String username, List<CartItem> orderDetails);

	List<Order> findAll();
	
	List<Order> findByStatus(int status);

	Order findById(int id);

	void delete(int id);

	void update(int id);

	void update2(int id);
	
	List<Order> findAllByUserId(int userId);
	
	Page<Order> findPaginated(List<Order> products, Pageable pageable);
}