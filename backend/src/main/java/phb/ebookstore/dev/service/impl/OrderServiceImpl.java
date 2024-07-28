package phb.ebookstore.dev.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import phb.ebookstore.dev.dto.CustomOrderResponse;
import phb.ebookstore.dev.entity.User;
import phb.ebookstore.dev.enumric.OrderStatus;
import phb.ebookstore.dev.enumric.PaymentMethod;
import phb.ebookstore.dev.model.Book;
import phb.ebookstore.dev.model.Order;
import phb.ebookstore.dev.model.OrderItem;
import phb.ebookstore.dev.model.ShipOrder;
import phb.ebookstore.dev.repository.BookRepository;
import phb.ebookstore.dev.repository.CartItemRepository;
import phb.ebookstore.dev.repository.OrderItemRepository;
import phb.ebookstore.dev.repository.OrderRepository;
import phb.ebookstore.dev.repository.OrderStatusRepository;
import phb.ebookstore.dev.repository.ShipOrderRepository;
import phb.ebookstore.dev.security.dto.CartItemBook;
import phb.ebookstore.dev.security.dto.OrderItemBook;
import phb.ebookstore.dev.security.dto.OrderRequestDTO;
import phb.ebookstore.dev.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private ShipOrderRepository shipOrderRepository;
	@Autowired
	private OrderStatusRepository orderStatusRepository;

	@Override
	@Transactional
	public Order createOrder(OrderRequestDTO orderRequestDTO, User user) {
		// totalProductPrice
		// totalDiscountPrice
		// totalShippingPrice
		// totalPrice
		// note
		// paymentMethod
		// orderStatus
		
		// 01. Tạo Order
		// 02. Tạo OrderItem
		// 03. Tạo OrderStatus
		// 04. Cập nhật giỏ hàng
		double totalShippingPrice = 32000;
		double totalDiscountPrice = 0;
		double totalProductPrice = 0;
		List<CartItemBook> bookItems = orderRequestDTO.getBookItems();
		for (CartItemBook bookItem : bookItems) {
			Book buyBook = bookRepository.findById(bookItem.getBookId()).orElse(null);
			double totalBuyBookPrice = buyBook.getPrice() * bookItem.getQuantity();
			totalProductPrice += totalBuyBookPrice;
		}
		double totalPrice = totalProductPrice + totalShippingPrice - totalDiscountPrice;

		OrderStatus orderStatus = OrderStatus.AWAIT_PAYMENT;
		PaymentMethod paymentMethod = orderRequestDTO.getPaymentMethod();
		if(paymentMethod.equals(PaymentMethod.COD) || paymentMethod.equals(PaymentMethod.EBOOK_PAYLATER)) {// Nếu là COD thì chờ xác nhận lun
			orderStatus = OrderStatus.AWAIT_ACCEPT;
		}
		Order order = Order
				.builder()
				.orderDate(new Date())
				.totalProductPrice(totalProductPrice)
				.totalShippingPrice(totalShippingPrice)
				.totalDiscountPrice(totalDiscountPrice)
				.totalPrice(totalPrice)
				.note(orderRequestDTO.getNote())
				.paymentMethod(orderRequestDTO.getPaymentMethod())
				.orderStatus(orderStatus)
				.deliveryAddress(orderRequestDTO.getDeliveryAddress())
				.user(user)
				.build();
		Order savedOrder = orderRepository.save(order);
		for (CartItemBook bookItem : bookItems) {
			Book buyBook = bookRepository.findById(bookItem.getBookId()).orElse(null);
			double totalBuyBookPrice = buyBook.getPrice() * bookItem.getQuantity();
			float cbm = buyBook.getLength() * buyBook.getWidth() * buyBook.getHeight() / 6000;
			OrderItem orderItem = OrderItem
					.builder()
					.book(buyBook)
					.quantity(bookItem.getQuantity())
					.price(totalBuyBookPrice)
					.totalWeightCBM(cbm)
					.order(savedOrder)
					.build();
			orderItemRepository.save(orderItem);
			if(bookItem.getCartItemId() != 0) {// Xóa cartItem ra khỏi giỏ hàng
				cartItemRepository.deleteById(bookItem.getCartItemId());
			}
		}
		
		phb.ebookstore.dev.model.OrderStatus theOrderStatus = phb.ebookstore.dev.model.OrderStatus
				.builder()
				.orderStatus(orderStatus)
				.time(new Date())
				.order(savedOrder)
				.description("Đang đợi người bán xác nhận ⏳")
				.classIcon("fas fa-check-double")
				.build();
		orderStatusRepository.save(theOrderStatus);
		
		// Bước này sẽ cần admin xác nhận đơn hàng rồi mới tạo ShipOrder
//		double codPrice = 0;
//		if(orderRequestDTO.getPaymentMethod().equals(PaymentMethod.COD)) {
//			codPrice = totalPrice;
//		}
//		ShipOrder shipOrder = ShipOrder
//				.builder()
//				.billingAddress("EBook Store ECommerial TNHH Địa chỉ tòa Chung cư Tower Water Quận 1, Đường Dakao, Phú Nhuận, TP.HCM")
//				.deliveryAddress(orderRequestDTO.getDeliveryAddress())
//				.emailAddress(null)
//				.phoneNumber(null)
//				.maxWeight(21000)
//				.codPrice(totalPrice)
//				.signature(null)
//				.build();
//		shipOrderRepository.save(shipOrder);
		return savedOrder;
	}

	@Override
	public List<CustomOrderResponse> findAllByUser(User user) {
		List<Order> orders = orderRepository.findAllByUserOrderByIdDesc(user);
		List<CustomOrderResponse> listOrderDTO = new ArrayList<CustomOrderResponse>();
		for (Order order : orders) {
			// Lấy mảng orderStatuses
			List<phb.ebookstore.dev.model.OrderStatus> orderStatuses = order.getOrderStatuses();
			orderStatuses.sort(Comparator.comparing(phb.ebookstore.dev.model.OrderStatus::getId).reversed());
			
			// Lấy shipOrder
			ShipOrder shipOrder = shipOrderRepository.findByOrder(order);
			
			// Lấy mảng orderItemBooks
			List<OrderItemBook> orderItemBooks = new ArrayList<OrderItemBook>();
			List<OrderItem> orderItems = order.getItems();
			for (OrderItem orderItem : orderItems) {
				OrderItemBook orderItemBook = OrderItemBook
						.builder()
						.orderItem(orderItem)
						.book(orderItem.getBook())
						.build();
				orderItemBooks.add(orderItemBook);
			}
			
			CustomOrderResponse dto = CustomOrderResponse
					.builder()
					.order(order)
					.orderItemBooks(orderItemBooks)
					.orderStatuses(orderStatuses)
					.shipOrder(shipOrder)
					.build();
			listOrderDTO.add(dto);
		}
		return listOrderDTO;
	}
	
	@Override
	public List<CustomOrderResponse> findAllByUserAndOrderStatus(OrderStatus orderStatus, User user) {
		List<Order> orders = orderRepository.findAllByUserAndOrderStatusOrderByIdDesc(user, orderStatus);
		List<CustomOrderResponse> listOrderDTO = new ArrayList<CustomOrderResponse>();
		for (Order order : orders) {
			// Lấy mảng orderStatuses
			List<phb.ebookstore.dev.model.OrderStatus> orderStatuses = order.getOrderStatuses();
			orderStatuses.sort(Comparator.comparing(phb.ebookstore.dev.model.OrderStatus::getId).reversed());
			
			// Lấy shipOrder
			ShipOrder shipOrder = shipOrderRepository.findByOrder(order);
			
			// Lấy mảng orderItemBooks
			List<OrderItemBook> orderItemBooks = new ArrayList<OrderItemBook>();
			List<OrderItem> orderItems = order.getItems();
			for (OrderItem orderItem : orderItems) {
				OrderItemBook orderItemBook = OrderItemBook
						.builder()
						.orderItem(orderItem)
						.book(orderItem.getBook())
						.build();
				orderItemBooks.add(orderItemBook);
			}
			
			CustomOrderResponse dto = CustomOrderResponse
					.builder()
					.order(order)
					.orderItemBooks(orderItemBooks)
					.orderStatuses(orderStatuses)
					.shipOrder(shipOrder)
					.build();
			listOrderDTO.add(dto);
		}
		return listOrderDTO;
	}

	@Override
	public List<CustomOrderResponse> findAll() {
		List<Order> orders = orderRepository.findAllOrderByIdDesc();
		List<CustomOrderResponse> listOrderDTO = new ArrayList<CustomOrderResponse>();
		for (Order order : orders) {
			// Lấy mảng orderStatuses
			List<phb.ebookstore.dev.model.OrderStatus> orderStatuses = order.getOrderStatuses();
			orderStatuses.sort(Comparator.comparing(phb.ebookstore.dev.model.OrderStatus::getId).reversed());
			
			// Lấy shipOrder
			ShipOrder shipOrder = shipOrderRepository.findByOrder(order);
			
			// Lấy mảng orderItemBooks
			List<OrderItemBook> orderItemBooks = new ArrayList<OrderItemBook>();
			List<OrderItem> orderItems = order.getItems();
			for (OrderItem orderItem : orderItems) {
				OrderItemBook orderItemBook = OrderItemBook
						.builder()
						.orderItem(orderItem)
						.book(orderItem.getBook())
						.build();
				orderItemBooks.add(orderItemBook);
			}
			
			CustomOrderResponse dto = CustomOrderResponse
					.builder()
					.order(order)
					.orderItemBooks(orderItemBooks)
					.orderStatuses(orderStatuses)
					.shipOrder(shipOrder).user(order.getUser())
					.build();
			listOrderDTO.add(dto);
		}
		return listOrderDTO;
	}

	@Override
	public List<CustomOrderResponse> findAllByOrderStatus(OrderStatus orderStatus) {
		List<Order> orders = orderRepository.findAllByOrderStatusOrderByIdDesc(orderStatus);
		List<CustomOrderResponse> listOrderDTO = new ArrayList<CustomOrderResponse>();
		for (Order order : orders) {
			// Lấy mảng orderStatuses
			List<phb.ebookstore.dev.model.OrderStatus> orderStatuses = order.getOrderStatuses();
			orderStatuses.sort(Comparator.comparing(phb.ebookstore.dev.model.OrderStatus::getId).reversed());
			
			// Lấy shipOrder
			ShipOrder shipOrder = shipOrderRepository.findByOrder(order);
			
			// Lấy mảng orderItemBooks
			List<OrderItemBook> orderItemBooks = new ArrayList<OrderItemBook>();
			List<OrderItem> orderItems = order.getItems();
			for (OrderItem orderItem : orderItems) {
				OrderItemBook orderItemBook = OrderItemBook
						.builder()
						.orderItem(orderItem)
						.book(orderItem.getBook())
						.build();
				orderItemBooks.add(orderItemBook);
			}
			
			CustomOrderResponse dto = CustomOrderResponse
					.builder()
					.order(order)
					.orderItemBooks(orderItemBooks)
					.orderStatuses(orderStatuses)
					.shipOrder(shipOrder)
					.user(order.getUser())
					.build();
			listOrderDTO.add(dto);
		}
		return listOrderDTO;
	}

	@Override
	public void confirmOrder(long orderId) {
		Order order = orderRepository.findById(orderId).orElse(null);
		order.setOrderStatus(OrderStatus.PREPARING);
		
		phb.ebookstore.dev.model.OrderStatus orderStatus = phb.ebookstore.dev.model.OrderStatus
				.builder()
				.orderStatus(OrderStatus.PREPARING)
				.time(new Date())
				.order(order)
				.description("Người bán đã xác nhận đơn hàng và đang chuẩn bị gói hàng")
				.classIcon("fas fa-cubes")
				.build();
		orderStatusRepository.save(orderStatus);
		
		double codPrice = order.getTotalPrice();
		if(!order.getPaymentMethod().equals(PaymentMethod.COD)) {
			codPrice = 0;
		}
		ShipOrder shipOrder = ShipOrder
				.builder()
				.billingAddress("60-62 Lê Lợi, P. Bến Nghé, Q1, TP Hồ Chí Minh")
				.codPrice(codPrice)
				.deliveryAddress(order.getDeliveryAddress())
				.emailAddress(order.getUser().getEmail())
				.phoneNumber(order.getUser().getPhoneNumber())
				.signature(null)
				.order(order)
				.maxWeight(21000)
				.build();
		shipOrderRepository.save(shipOrder);
		orderRepository.save(order);
	}

	@Override
	public void handOverOrder(long orderId) {
		Order order = orderRepository.findById(orderId).orElse(null);
		order.setOrderStatus(OrderStatus.TRANSPORTING);
		
		phb.ebookstore.dev.model.OrderStatus orderStatus = phb.ebookstore.dev.model.OrderStatus
				.builder()
				.orderStatus(OrderStatus.TRANSPORTING)
				.time(new Date())
				.order(order)
				.description("Người bán đã bàn giao kiện hàng cho đơn vị vận chuyển")
				.classIcon("fas fa-cubes")
				.build();
		orderStatusRepository.save(orderStatus);
		orderRepository.save(order);
	}

	@Override
	public Order cancelOrder(long orderId, String reason) {
		String decodedText = "Người mua đã bấm hủy đơn hàng ❌";
		if(reason != null) {
			decodedText = "Đơn hàng đã bị hủy bởi người bán. Vì lý do: " + java.net.URLDecoder.decode(reason, StandardCharsets.UTF_8);
		}
		System.out.println("decodedText==" + decodedText);
		Order order = orderRepository.findById(orderId).orElse(null);
		order.setOrderStatus(OrderStatus.CANCELED);
		
		phb.ebookstore.dev.model.OrderStatus orderStatus = phb.ebookstore.dev.model.OrderStatus
				.builder()
				.orderStatus(OrderStatus.CANCELED)
				.time(new Date())
				.order(order)
				.description(decodedText)
				.classIcon("fas fa-times text-danger")
				.build();
		orderStatusRepository.save(orderStatus);
		return orderRepository.save(order);
	}
}
