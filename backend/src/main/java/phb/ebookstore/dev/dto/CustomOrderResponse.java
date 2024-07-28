package phb.ebookstore.dev.dto;

import java.util.List;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import phb.ebookstore.dev.entity.User;
import phb.ebookstore.dev.model.Order;
import phb.ebookstore.dev.model.OrderStatus;
import phb.ebookstore.dev.model.ShipOrder;
import phb.ebookstore.dev.security.dto.OrderItemBook;

@Data
@Builder
public class CustomOrderResponse {
	private Order order;
	private List<OrderItemBook> orderItemBooks;
	private List<OrderStatus> orderStatuses;
	@Nullable
	private ShipOrder shipOrder;
	@Nullable
	private User user;
}
