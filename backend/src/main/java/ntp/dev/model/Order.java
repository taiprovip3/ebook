/*
 * 1 Order có thể chứa nhiều Cuốn Sách khác nhau
 * Mỗi 1 sách sẽ là 1 OrderItem
 * Order đc tạo ra sau khi chọn PaymentMethod và Áp dụng voucher
 * */
package ntp.dev.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ntp.dev.entity.User;
import ntp.dev.enumric.OrderStatus;
import ntp.dev.enumric.PaymentMethod;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date orderDate;
	private double totalProductPrice;
	private double totalDiscountPrice;// Tiền giảm từ Voucher
	private double totalShippingPrice;
    private double totalPrice;
    private String note;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;// Trạng thái hiện tại của đơn hàng # với tất cả trạng thái của đơn hạng xuyên xuốt 
    private String deliveryAddress;// Địa chỉ deliveryAddress này khác deliveryAddress trong ShipOrder vì có thể KH muốn đổi sang chỉa chỉ ship khác khi mua sách
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    @ToString.Exclude
    private User user;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<OrderItem> items;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<ntp.dev.model.OrderStatus> orderStatuses;
}
