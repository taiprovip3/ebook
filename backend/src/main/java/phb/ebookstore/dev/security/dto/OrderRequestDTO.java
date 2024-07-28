package phb.ebookstore.dev.security.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import phb.ebookstore.dev.enumric.PaymentMethod;

@Data
public class OrderRequestDTO {
	@NotNull
    private List<CartItemBook> bookItems;
	@NotBlank
    private String deliveryAddress;
    private String note;
    private PaymentMethod paymentMethod;
}