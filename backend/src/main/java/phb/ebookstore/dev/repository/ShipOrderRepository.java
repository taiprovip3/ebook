package phb.ebookstore.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import phb.ebookstore.dev.model.Order;
import phb.ebookstore.dev.model.ShipOrder;

@Repository
public interface ShipOrderRepository extends JpaRepository<ShipOrder, Long> {

	public ShipOrder findByOrder(Order order);
}
