package phb.ebookstore.dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import phb.ebookstore.dev.entity.Role;
import phb.ebookstore.dev.entity.User;
import phb.ebookstore.dev.model.Book;
import phb.ebookstore.dev.model.OrderItemRating;
import phb.ebookstore.dev.security.config.JwtService;
import phb.ebookstore.dev.security.dto.SaveBookDTO;
import phb.ebookstore.dev.service.BookService;
import phb.ebookstore.dev.service.OrderItemRatingService;
import phb.ebookstore.dev.service.OrderItemService;
import phb.ebookstore.dev.service.UserService;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {
	
	@Autowired
	private UserService userService;
	@Autowired
    private JwtService jwtUtil;

	@Autowired
	private BookService bookService;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private OrderItemRatingService orderItemRatingService;
	
	@GetMapping("/{bookId}")
	public ResponseEntity<?> getBookById(@PathVariable long bookId) {
		return ResponseEntity.ok(bookService.findById(bookId));
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> listBook() {
		return ResponseEntity.ok(bookService.listBook());
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> searchBook(@RequestParam String key) {
		List<Book> books = bookService.searchBook(key);
		return ResponseEntity.ok(books);
	}
	@GetMapping("/flashSaleBooks")
    public List<Book> getBooksOnFlashSale() {
        return bookService.getBooksOnFlashSale();
    }
	
	@GetMapping("/topSoldBooks")
	public ResponseEntity<?> findTopSellingBooks() {
		return ResponseEntity.ok(orderItemService.findTopSellingBooks());
	}
	
	@GetMapping("/getTotalSoldOfBook/{bookId}")
	public int getTotalSoldOfBook(@PathVariable long bookId) {
		return orderItemService.getTotalSoldOfBook(bookId);
	}
	
	@GetMapping("/getRatingsOfBook/{bookId}")
	public List<OrderItemRating> getRatingsOfBook(@PathVariable long bookId) {
		return orderItemRatingService.getRatingsOfBook(bookId);
	}
	
	
	// Admin/Management using
	@PostMapping("/upload-image")
    public ResponseEntity<?> uploadBookCoverImage(@RequestHeader("Authorization") String token, @RequestParam("file") MultipartFile file) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(user.getRole().equals(Role.USER)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized Admin or Management role");
		}
		
		try {
			String fileUrl = bookService.uploadBookCoverImage(file);
			return ResponseEntity.ok(fileUrl);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
		}
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveBook(@RequestHeader("Authorization") String token, @RequestBody SaveBookDTO saveBookDTO) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(user.getRole().equals(Role.USER)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized Admin or Management role");
		}
		
		System.out.println("--------------saveBookDTO---------" + saveBookDTO);
		return ResponseEntity.ok(bookService.saveBook(saveBookDTO));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteBook(@RequestHeader("Authorization") String token, @PathVariable long id) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(user.getRole().equals(Role.USER)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized Admin or Management role");
		}
		
		return ResponseEntity.ok(bookService.deleteBookById(id));
	}
	
	@DeleteMapping("/deleteMany")
	public ResponseEntity<?> deleteManyBook(@RequestHeader("Authorization") String token, @RequestBody List<Long> bookIds) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
		String username = jwtUtil.extractUsername(token); // Là username do trong UserDetail của security nó quy định là username
		User user = userService.getUserByEmail(username);
		if(user.getRole().equals(Role.USER)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized Admin or Management role");
		}
		
		try {
            return ResponseEntity.ok(bookService.deleteBooksByIds(bookIds));
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete books.");
        }
	}
}
