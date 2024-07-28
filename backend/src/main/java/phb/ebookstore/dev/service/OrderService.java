package phb.ebookstore.dev.service;

import java.util.List;

import phb.ebookstore.dev.dto.CustomOrderResponse;
import phb.ebookstore.dev.entity.User;
import phb.ebookstore.dev.enumric.OrderStatus;
import phb.ebookstore.dev.model.Order;
import phb.ebookstore.dev.security.dto.OrderRequestDTO;

public interface OrderService {
	public Order createOrder(OrderRequestDTO orderRequestDTO, User user);

	public List<CustomOrderResponse> findAllByUser(User user);
	
	public List<CustomOrderResponse> findAllByUserAndOrderStatus(OrderStatus orderStatus, User user);

	public List<CustomOrderResponse> findAll();
	public List<CustomOrderResponse> findAllByOrderStatus(OrderStatus orderStatus);
	
	public void confirmOrder(long orderId);

	public void handOverOrder(long orderId);

	public Order cancelOrder(long orderId, String reason);
}
