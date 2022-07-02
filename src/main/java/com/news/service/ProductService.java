package com.news.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.news.entity.Product;
import com.news.entity.Supply;

public interface ProductService {
	List<Product> list();

	Product create(Product category);

	Product update(int id, Product category);

	Product delete(int id);

	Product findById(int id);

	List<Product> findNewest();

	List<Product> findByPin(boolean pin);

	List<Product> findByCategoryId(int categoryId);

	List<Supply> findBySupplyId(int supplyId);

	List<Product> findByProductName(String productName);

	Page<Product> findPaginated(List<Product> products, Pageable pageable);
}
