package phb.ebookstore.dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import phb.ebookstore.dev.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	@Query("SELECT b FROM Book b WHERE b.flashSale > 0 AND b.flashSaleExpired > CURRENT_TIMESTAMP")
    public List<Book> findBooksOnFlashSale();

	@Query("SELECT COUNT(*) AS TOTAL FROM OrderItem oi WHERE oi.book.id = :bookId")
	public int getTotalSoldOfBook(@Param("bookId") long bookId);
}
