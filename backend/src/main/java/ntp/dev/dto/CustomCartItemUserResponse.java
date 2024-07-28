package ntp.dev.dto;

import lombok.Builder;
import lombok.Data;
import ntp.dev.model.Book;
import ntp.dev.model.CartItem;

@Data
@Builder
public class CustomCartItemUserResponse {
	private CartItem cartItem;
	private Book book;
}
