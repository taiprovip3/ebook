package phb.ebookstore.dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import phb.ebookstore.dev.model.Book;
import phb.ebookstore.dev.model.Cart;
import phb.ebookstore.dev.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	@Query(value = "SELECT ci.* FROM cart_items ci JOIN carts c ON ci.cart_id = c.id WHERE c.user_id = :userId ORDER BY ci.id DESC", nativeQuery = true)
	public List<CartItem> getUserCartItems(@Param("userId") long userId);
	public CartItem findFirstByCartAndBook(Cart cart, Book book);
}
