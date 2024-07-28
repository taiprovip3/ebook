package ntp.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntp.dev.model.Order;
import ntp.dev.model.ShipOrder;

@Repository
public interface ShipOrderRepository extends JpaRepository<ShipOrder, Long> {

	public ShipOrder findByOrder(Order order);
}
