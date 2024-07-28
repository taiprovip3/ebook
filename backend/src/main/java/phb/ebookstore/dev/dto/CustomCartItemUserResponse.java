package phb.ebookstore.dev.dto;

import lombok.Builder;
import lombok.Data;
import phb.ebookstore.dev.model.Book;
import phb.ebookstore.dev.model.CartItem;

@Data
@Builder
public class CustomCartItemUserResponse {
	private CartItem cartItem;
	private Book book;
}
