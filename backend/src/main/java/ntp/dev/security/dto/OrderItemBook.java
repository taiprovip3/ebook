package ntp.dev.security.dto;

import lombok.Builder;
import lombok.Data;
import ntp.dev.model.Book;
import ntp.dev.model.OrderItem;

@Data
@Builder
public class OrderItemBook {// Class này là custom dto cho CustomOrderResponse.java
	private OrderItem orderItem;
	private Book book;
}
