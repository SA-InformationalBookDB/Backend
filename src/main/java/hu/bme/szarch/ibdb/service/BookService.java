package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.domain.Book;
import hu.bme.szarch.ibdb.domain.Category;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.BookRepository;
import hu.bme.szarch.ibdb.service.dto.book.BookResult;
import hu.bme.szarch.ibdb.service.dto.book.CreateBookMessage;
import hu.bme.szarch.ibdb.service.dto.book.OfferedBookQuery;
import hu.bme.szarch.ibdb.service.dto.book.UpdateBookMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;
    private UserService userService;

    public BookService(BookRepository bookRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    public Page<BookResult> getPopulars(Pageable pageable) {
        return bookRepository.findAllByOrderByViewsDesc(pageable).map(this::bookToResult);
    }

    public Page<BookResult> getBestSellers(Pageable pageable) {
        return bookRepository.findAllByOrderBySoldDesc(pageable).map(this::bookToResult);
    }

    public Page<BookResult> getTrending(Pageable pageable, OffsetDateTime publishedAfter) {
        return bookRepository.findAllByPublishedAfterOrderByViewsDesc(pageable, publishedAfter).map(this::bookToResult);
    }

    public Page<BookResult> findOffered(OfferedBookQuery query) {
        List<Category> categories = userService.getCategoriesForUser(query.getUserId());

        return bookRepository.findAllByPublishedAfterAndCategoriesContainsAndAuthorInOrderByViewsDesc(
                query.getPageable(),
                query.getPublishedAfter(),
                new HashSet<>(categories),
                new HashSet<>(query.getAuthors())
        ).map(this::bookToResult);
    }

    public BookResult getBook(String id) {
        Optional<Book> book = bookRepository.findById(id);

        if (!book.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        return bookToResult(book.get());
    }

    public BookResult findById(String id) {
        Optional<Book> book = bookRepository.findById(id);

        if (!book.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        return bookToResult(book.get());
    }

    public Page<BookResult> findByTitle(Pageable pageable, String queryString) {
        return bookRepository.findAllByTitleContains(pageable, queryString).map(this::bookToResult);
    }

    public BookResult addBook(CreateBookMessage message) {
        Book book = new Book();

        book.setAuthor(message.getAuthor());
        book.setImageUrl(message.getImageUrl());
        book.setPageNumber(message.getPageNumber());
        book.setPublished(message.getPublished());
        book.setPublisher(message.getPublisher());
        book.setSold(message.getSold());
        book.setSummary(message.getSummary());
        book.setTitle(message.getTitle());
        book.setViews(message.getViews());

        Book createdBook = bookRepository.save(book);

        return BookResult.builder()
                .id(createdBook.getId())
                .author(message.getAuthor())
                .imageUrl(message.getImageUrl())
                .pageNumber(message.getPageNumber())
                .published(message.getPublished())
                .publisher(message.getPublisher())
                .sold(message.getSold())
                .summary(message.getSummary())
                .title(message.getTitle())
                .views(message.getViews())
                .build();
    }


    public void deleteBook(String bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if (!book.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        bookRepository.delete(book.get());
    }

    public BookResult updateBook(UpdateBookMessage message) {
        Optional<Book> book = bookRepository.findById(message.getId());

        if(!book.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        book.get().setAuthor(message.getAuthor());
        book.get().setImageUrl(message.getImageUrl());
        book.get().setPageNumber(message.getPageNumber());
        book.get().setPublished(message.getPublished());
        book.get().setPublisher(message.getPublisher());
        book.get().setSold(message.getSold());
        book.get().setSummary(message.getSummary());
        book.get().setTitle(message.getTitle());
        book.get().setViews(message.getViews());

        bookRepository.save(book.get());

        return BookResult.builder()
                .id(book.get().getId())
                .author(message.getAuthor())
                .imageUrl(message.getImageUrl())
                .pageNumber(message.getPageNumber())
                .published(message.getPublished())
                .publisher(message.getPublisher())
                .sold(message.getSold())
                .summary(message.getSummary())
                .title(message.getTitle())
                .views(message.getViews())
                .build();
    }

    private BookResult bookToResult(Book book) {
        return BookResult.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .imageUrl(book.getImageUrl())
                .pageNumber(book.getPageNumber())
                .published(book.getPublished())
                .publisher(book.getPublisher())
                .sold(book.getSold())
                .summary(book.getSummary())
                .title(book.getTitle())
                .views(book.getViews())
                .build();
    }

}
