package phb.ebookstore.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import phb.ebookstore.dev.entity.User;
import phb.ebookstore.dev.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	public Cart findByUser(User user);
}
