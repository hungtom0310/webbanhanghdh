package com.news.service;

import java.util.List;

import com.news.entity.Supply;

public interface SupplyService {
	List<Supply> list();

	Supply createOrUpdate(Supply supply);

	Supply delete(int id);

	Supply findById(int id);

}
