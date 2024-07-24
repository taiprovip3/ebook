package ntp.dev.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ntp.dev.model.Book;
import ntp.dev.security.dto.SaveBookDTO;

public interface BookService {
	public String uploadBookCoverImage(MultipartFile file);
	public Book saveBook(SaveBookDTO bookDTO);
	public List<Book> listBook();
	public boolean deleteBookById(long id);
	public Boolean deleteBooksByIds(List<Long> bookIds);
}
