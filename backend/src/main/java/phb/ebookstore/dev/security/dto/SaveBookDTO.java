package phb.ebookstore.dev.security.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;
import phb.ebookstore.dev.enumric.BookType;
import phb.ebookstore.dev.enumric.Genre;

@Data
@Builder
public class SaveBookDTO {
	private long bookId;
	private String title;
	private String author;
	private String coverImageUrl;
	private String description;
	private double discount;
	private double flashSale;
	private Timestamp flashSaleExpired;
	private Genre genre;
	private String isbn;
	private double price;
	private Timestamp publicationDate;
	private String publisher;
	private int stockQuantity;
	private BookType bookType;
	private int length;
	private int width;
	private int height;
	private int weight;
}
