package phb.ebookstore.dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import phb.ebookstore.dev.model.OrderItem;
import phb.ebookstore.dev.security.dto.BookSoldDTO;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	@Query("SELECT new phb.ebookstore.dev.security.dto.BookSoldDTO(oi.book.id, COUNT(oi.book.id)) FROM OrderItem oi GROUP BY oi.book.id ORDER BY COUNT (oi.book.id) DESC")
	public List<BookSoldDTO> findTopSellingBooks();
}
