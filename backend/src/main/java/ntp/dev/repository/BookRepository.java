package ntp.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntp.dev.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
