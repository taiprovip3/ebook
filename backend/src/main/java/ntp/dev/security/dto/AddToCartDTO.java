package ntp.dev.security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddToCartDTO {
	private long bookId;
	private int quantity;
}
