package ntp.dev.service;

import java.util.List;
import java.util.Map;

public interface OrderItemService {
	public List<Map<String, Object>> findTopSellingBooks();
	public int getTotalSoldOfBook(long bookId);
}
