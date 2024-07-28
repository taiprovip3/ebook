package ntp.dev.service;

import java.util.List;

import ntp.dev.dto.CustomOrderResponse;
import ntp.dev.entity.User;
import ntp.dev.enumric.OrderStatus;
import ntp.dev.model.Order;
import ntp.dev.security.dto.OrderRequestDTO;

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
