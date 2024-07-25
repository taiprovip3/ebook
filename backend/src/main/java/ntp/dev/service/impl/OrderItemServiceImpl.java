package ntp.dev.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntp.dev.model.Book;
import ntp.dev.repository.BookRepository;
import ntp.dev.repository.OrderItemRepository;
import ntp.dev.security.dto.BookSoldDTO;
import ntp.dev.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public List<Map<String, Object>> findTopSellingBooks() {
		/*
		 * Giải thuật như sau:
		 * 1. Lấy ra id, totalSold của tất cả những cuốn sách bán chạy
		 * 2. Lấy ra tất cả object book (chứa full thông tin về book) từ các id bookd trên
		 * 3. Put object book ứng với id book sold vào mảng result rồi return
		 * */
		List<BookSoldDTO> soldBooks = orderItemRepository.findTopSellingBooks();
        
        List<Long> bookIds = soldBooks.stream()
                                      .map(BookSoldDTO::getBookId)
                                      .collect(Collectors.toList());
        
        List<Book> books = bookRepository.findAllById(bookIds);
        
//        Map<Long, Book> bookMap = books.stream()
//                .collect(Collectors.toMap(Book::getId, book -> book));
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (BookSoldDTO bookSold: soldBooks) {
        	Long bookId = bookSold.getBookId();
            Book book = null;
            
            for (Book b : books) {// Lấy ra book từ bookId
                if (b.getId() == bookId) {
                    book = b;
                    break;
                }
            }
            
            if (book != null) {
                // Tạo bản đồ chứa thông tin sách và số lượng đã bán
                Map<String, Object> bookMap = new HashMap<>();
                bookMap.put("book", book);
                bookMap.put("totalSold", bookSold.getTotalSold());
                result.add(bookMap);
            }
		}
		return result;
	}

	@Override
	public int getTotalSoldOfBook(long bookId) {
		return bookRepository.getTotalSoldOfBook(bookId);
	}
	
}
