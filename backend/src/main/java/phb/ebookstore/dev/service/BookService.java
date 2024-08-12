package phb.ebookstore.dev.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import phb.ebookstore.dev.model.Book;
import phb.ebookstore.dev.security.dto.SaveBookDTO;

public interface BookService {
	public String uploadBookCoverImage(MultipartFile file);
	public Book saveBook(SaveBookDTO bookDTO);
//	public List<Book> listBook();
	public Page<Book> listBook(Pageable pageable);
	public boolean deleteBookById(long id);
	public Boolean deleteBooksByIds(List<Long> bookIds);
	public List<Book> getBooksOnFlashSale();
	public Book findById(long bookId);
	public List<Book> searchBook(String key);
}
