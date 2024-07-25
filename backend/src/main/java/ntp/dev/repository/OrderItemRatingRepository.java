package ntp.dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ntp.dev.model.OrderItemRating;

@Repository
public interface OrderItemRatingRepository extends JpaRepository<OrderItemRating, Long> {
	@Query(value = "SELECT oir.* FROM order_item_rating oir JOIN order_items oi ON oir.order_item_id = oi.id WHERE oi.book_id = :bookId", nativeQuery = true)
	public List<OrderItemRating> getRatingsOfBook(@Param("bookId") long bookId);
}
