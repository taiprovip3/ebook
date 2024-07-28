package phb.ebookstore.dev.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import phb.ebookstore.dev.model.Book;
import phb.ebookstore.dev.repository.BookRepository;
import phb.ebookstore.dev.security.dto.SaveBookDTO;
import phb.ebookstore.dev.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Value("${minio.endpoint}")
    private String minioEndpoint;

    @Value("${minio.accessKey}")
    private String minioAccessKey;

    @Value("${minio.secretKey}")
    private String minioSecretKey;

    @Value("${minio.bucketName}")
    private String bucketName;
    
    @Autowired
    private BookRepository bookRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
	@Override
	public String uploadBookCoverImage(MultipartFile file) {
		try {
            MinioClient minioClient = new MinioClient.Builder()
                    .endpoint(minioEndpoint)
                    .credentials(minioAccessKey, minioSecretKey)
                    .build();

            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            String fileUrl = minioEndpoint + "/" + bucketName + "/" + fileName;
            return fileUrl;
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
	}

	@Override
	public Book saveBook(SaveBookDTO bookDTO) {
		System.out.println("bookDTO===" + bookDTO);
		Book book = bookRepository.findById(bookDTO.getBookId()).orElse(null);
		if(book == null) {// Save
			System.out.println("isSave");
		} else {
			// Update
			System.out.println("isUpdate");
		}
		String theDefaultCoverImageUrl = "https://cantho-school.fpt.edu.vn/wp-content/uploads/Screenshot-2023-09-28-at-15.59.13.png";
		String coverImageUrl = bookDTO.getCoverImageUrl();
		if(coverImageUrl != null) {
			theDefaultCoverImageUrl = coverImageUrl;
		}
		Book b = Book
				.builder()
				.id(bookDTO.getBookId())
				.author(bookDTO.getAuthor())
				.coverImageUrl(theDefaultCoverImageUrl)
				.description(bookDTO.getDescription())
				.discount(bookDTO.getDiscount())
				.flashSale(bookDTO.getFlashSale())
				.flashSaleExpired(bookDTO.getFlashSaleExpired())
				.genre(bookDTO.getGenre())
				.isbn(bookDTO.getIsbn())
				.price(bookDTO.getPrice())
				.publicationDate(bookDTO.getPublicationDate())
				.publisher(bookDTO.getPublisher())
				.stockQuantity(bookDTO.getStockQuantity())
				.title(bookDTO.getTitle())
				.bookType(bookDTO.getBookType())
				.length(bookDTO.getLength())
				.width(bookDTO.getWidth())
				.height(bookDTO.getHeight())
				.weight(bookDTO.getWeight())
				.build();
		return bookRepository.save(b);
	}

	@Override
	public List<Book> listBook() {
//	    Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id"));
//        return bookRepository.findAll(pageable).getContent();
		return bookRepository.findAll();
	}

	@Override
	@Transactional
	public boolean deleteBookById(long id) {
		try {
			bookRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public Boolean deleteBooksByIds(List<Long> bookIds) {
		try {
			bookRepository.deleteAllById(bookIds);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Book> getBooksOnFlashSale() {
		return bookRepository.findBooksOnFlashSale();
	}

	@Override
	public Book findById(long bookId) {
		return bookRepository.findById(bookId).orElse(null);
	}

	@Override
	public List<Book> searchBook(String key) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);

        List<Predicate> predicates = new ArrayList<>();

        if (key != null && !key.isEmpty()) {
            String keyPattern = "%" + key.toLowerCase() + "%";
            predicates.add(cb.or(
                    cb.like(cb.lower(book.get("title")), keyPattern),
                    cb.like(cb.lower(book.get("author")), keyPattern),
                    cb.like(cb.lower(book.get("genre").as(String.class)), keyPattern)
            ));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
	}
}
