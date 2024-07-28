package phb.ebookstore.dev.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ShipOrder implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	private String billingAddress;
	private String deliveryAddress;
	private String emailAddress;
	private String phoneNumber;
	
	private double maxWeight; // gram
	private double codPrice;
	private String signature;
	
	@OneToOne
	@JoinColumn(name = "order_id")
	private Order order;
}
