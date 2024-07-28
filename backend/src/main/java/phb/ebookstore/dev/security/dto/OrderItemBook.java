package phb.ebookstore.dev.security.dto;

import lombok.Builder;
import lombok.Data;
import phb.ebookstore.dev.model.Book;
import phb.ebookstore.dev.model.OrderItem;

@Data
@Builder
public class OrderItemBook {// Class này là custom dto cho CustomOrderResponse.java
	private OrderItem orderItem;
	private Book book;
}
