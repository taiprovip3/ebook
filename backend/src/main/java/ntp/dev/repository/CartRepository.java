package ntp.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntp.dev.entity.User;
import ntp.dev.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	public Cart findByUser(User user);
}
