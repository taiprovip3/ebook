package ntp.dev.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import ntp.dev.enumric.GenreGroup;
import ntp.dev.security.dto.SaveBookDTO;
import ntp.dev.service.BookService;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

	@Autowired
	private BookService bookService;
	
	@PostMapping("/upload-image")
    public ResponseEntity<?> uploadBookCoverImage(@RequestParam("file") MultipartFile file) {
		try {
			String fileUrl = bookService.uploadBookCoverImage(file);
			return ResponseEntity.ok(fileUrl);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> listBook() {
		return ResponseEntity.ok(bookService.listBook());
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveBook(@RequestBody SaveBookDTO saveBookDTO) {
		System.out.println("--------------saveBookDTO---------" + saveBookDTO);
		return ResponseEntity.ok(bookService.saveBook(saveBookDTO));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable long id) {
		return ResponseEntity.ok(bookService.deleteBookById(id));
	}
	
	@DeleteMapping("/deleteMany")
	public ResponseEntity<?> deleteManyBook(@RequestBody List<Long> bookIds) {
		try {
            return ResponseEntity.ok(bookService.deleteBooksByIds(bookIds));
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete books.");
        }
	}
}
