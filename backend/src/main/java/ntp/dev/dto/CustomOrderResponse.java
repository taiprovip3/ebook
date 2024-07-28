package ntp.dev.dto;

import java.util.List;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import ntp.dev.entity.User;
import ntp.dev.model.Order;
import ntp.dev.model.OrderStatus;
import ntp.dev.model.ShipOrder;
import ntp.dev.security.dto.OrderItemBook;

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
