package phb.ebookstore.dev.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import phb.ebookstore.dev.enumric.BookType;
import phb.ebookstore.dev.enumric.Genre;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String isbn;
    private String title;
    private String author;
    private String publisher;
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp publicationDate;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Enumerated(value = EnumType.STRING)
    private BookType bookType;
    private double price;
    private double discount;
    private int stockQuantity;
    @Column(columnDefinition = "TEXT")
    private String description;
    private double rating;
    private int reviewCount;
    private String coverImageUrl;
    
    private float length;
    private float width;
    private float height;
    private float weight; // gram
    
    private double flashSale;
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp flashSaleExpired;
    
    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<BookImage> images;
    
    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<CartItem> cartItems;
}
