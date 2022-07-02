package com.news.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.news.constant.ORDER_STATUS;
import com.news.entity.CartItem;
import com.news.entity.Order;
import com.news.entity.OrderDetail;
import com.news.entity.Product;
import com.news.entity.User;
import com.news.repository.ColorRepository;
import com.news.repository.OrderRepository;
import com.news.repository.ProductRepository;
import com.news.repository.SizeRepository;
import com.news.service.OrderService;
import com.news.service.ProductService;
import com.news.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private UserService userService;

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductService productService;

	@Autowired
	private SizeRepository sizeRepository;

	@Autowired
	private ColorRepository colorRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public void order(Order order, String username, List<CartItem> cartItems) {
		User user = userService.findByUsername(username);
		order.setUser(user);
		order.setCreatedAt(new Date());
		Set<OrderDetail> orderDetails = new HashSet<OrderDetail>();
		for (CartItem cartItem : cartItems) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(order);
			orderDetail.setPrice(cartItem.getProduct().getPrice().intValue());
			orderDetail.setProduct(productService.findById(cartItem.getProduct().getId()));
			orderDetail.setSize(sizeRepository.findById(cartItem.getSizeId().getId()).get());
			orderDetail.setQuantity(cartItem.getQuantity());
			orderDetail.setColor(colorRepository.getById(cartItem.getColorId().getId()));
			orderDetail.setAmount(cartItem.getProduct().getPrice().intValue() * cartItem.getQuantity());
			orderDetails.add(orderDetail);
		}
		order.setOrderDetails(orderDetails);
		order.setStatus(ORDER_STATUS.DAT_HANG);
		orderRepository.save(order);
	}

	@Override
	public List<Order> findAll() {

		return orderRepository.findAllByOrderByIdDesc();
	}

	@Override
	public Order findById(int id) {
		// TODO Auto-generated method stub
		return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Order Id:" + id));
	}

	@Override
	public void delete(int id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Order Id:" + id));
		order.setStatus(ORDER_STATUS.HUY);
		List<Product> products = new ArrayList<>();
		for (OrderDetail item : order.getOrderDetails()) {
			Product product = productService.findById(item.getProduct().getId());
			product.setQuantity(product.getQuantity() + item.getQuantity());
			products.add(product);
		}
		productRepository.saveAll(products);
		orderRepository.save(order);
	}

	@Override
	public void update(int id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Order Id:" + id));
		order.setStatus(ORDER_STATUS.DA_THANH_TOAN);
		List<Product> products = new ArrayList<>();
		for (OrderDetail item : order.getOrderDetails()) {
			Product product = productService.findById(item.getProduct().getId());
			product.setQuantity(product.getQuantity() - item.getQuantity());
			products.add(product);
		}
		productRepository.saveAll(products);
		orderRepository.save(order);
	}

	@Override
	public List<Order> findAllByUserId(int userId) {
		// TODO Auto-generated method stub
		return orderRepository.findByUserId(userId);
	}

	@Override
	public void update2(int id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Order Id:" + id));
		order.setStatus(ORDER_STATUS.DANG_GIAO_HANG);
		List<Product> products = new ArrayList<>();
		for (OrderDetail item : order.getOrderDetails()) {
			Product product = productService.findById(item.getProduct().getId());
			product.setQuantity(product.getQuantity() - item.getQuantity());
			products.add(product);
		}
		productRepository.saveAll(products);
		orderRepository.save(order);
		
	}

	@Override
	public List<Order> findByStatus(int status) {
		// TODO Auto-generated method stub
		return orderRepository.findByStatusOrderByIdDesc(status);
	}

	@Override
	public Page<Order> findPaginated(List<Order> products, Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Order> list;

		if (products.size() < startItem) {
			list = new ArrayList<>();
		} else {
			int toIndex = Math.min(startItem + pageSize, products.size());
			list = products.subList(startItem, toIndex);
		}

		Page<Order> bookPage = new PageImpl<Order>(list, PageRequest.of(currentPage, pageSize), products.size());

		return bookPage;
	}

}