package phb.ebookstore.dev.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookSoldDTO {
	 private Long bookId;
	 private Long totalSold;
}
