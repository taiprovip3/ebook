package ntp.dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ntp.dev.model.OrderItem;
import ntp.dev.security.dto.BookSoldDTO;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	@Query("SELECT new ntp.dev.security.dto.BookSoldDTO(oi.book.id, COUNT(oi.book.id)) FROM OrderItem oi GROUP BY oi.book.id ORDER BY COUNT (oi.book.id) DESC")
	public List<BookSoldDTO> findTopSellingBooks();
}
