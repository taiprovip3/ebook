package phb.ebookstore.dev.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CartItemBook {// Class này là custom dto request controller
	@NotBlank
    private long bookId;
	@NotBlank
    private int quantity;
	private long cartItemId;
}