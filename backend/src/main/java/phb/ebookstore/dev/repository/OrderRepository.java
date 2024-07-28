package phb.ebookstore.dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import phb.ebookstore.dev.entity.User;
import phb.ebookstore.dev.enumric.OrderStatus;
import phb.ebookstore.dev.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query(value = "SELECT * FROM orders ORDER BY id DESC", nativeQuery = true)
	public List<Order> findAllOrderByIdDesc();
	public List<Order> findAllByOrderStatusOrderByIdDesc(OrderStatus orderStatus);
	public List<Order> findAllByUserOrderByIdDesc(User user);
	public List<Order> findAllByUserAndOrderStatusOrderByIdDesc(User user, OrderStatus orderStatus);
}
