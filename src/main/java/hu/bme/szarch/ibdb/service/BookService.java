package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.domain.Book;
import hu.bme.szarch.ibdb.domain.Category;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.BookRepository;
import hu.bme.szarch.ibdb.repository.CategoryRepository;
import hu.bme.szarch.ibdb.service.dto.book.BookResult;
import hu.bme.szarch.ibdb.service.dto.book.CreateBookMessage;
import hu.bme.szarch.ibdb.service.dto.book.OfferedBookQuery;
import hu.bme.szarch.ibdb.service.dto.book.UpdateBookMessage;
import hu.bme.szarch.ibdb.service.dto.category.CategoryResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;
    private UserService userService;

    public BookService(BookRepository bookRepository,
                       CategoryRepository categoryRepository,
                       UserService userService) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    public List<BookResult> getPopulars() {
        return bookRepository.findAllByOrderByViewsDesc().stream().map(this::bookToResult).collect(Collectors.toList());
    }

    public List<BookResult> getTopPopulars() {
        return bookRepository.findAllByOrderByViewsDesc(PageRequest.of(0, 10)).map(this::bookToResult).getContent();
    }

    public List<BookResult> getBestSellers() {
        return bookRepository.findAllByOrderBySoldDesc().stream().map(this::bookToResult).collect(Collectors.toList());
    }

    public List<BookResult> getTopBestSellers() {
        return bookRepository.findAllByOrderBySoldDesc(PageRequest.of(0, 10)).map(this::bookToResult).getContent();
    }

    public List<BookResult> getTrending(OffsetDateTime publishedAfter) {
        return bookRepository.findAllByPublishedAfterOrderByViewsDesc(publishedAfter).stream().map(this::bookToResult).collect(Collectors.toList());
    }

    public List<BookResult> getTopTrending(OffsetDateTime publishedAfter) {
        return bookRepository.findAllByPublishedAfterOrderByViewsDesc(PageRequest.of(0, 10), publishedAfter).map(this::bookToResult).getContent();
    }

    public List<BookResult> findOffered(OfferedBookQuery query) {
        List<Category> categories = userService.getCategoriesForUser(query.getUserId());
        List<BookResult> books = userService.getFavourites(query.getUserId());

        List<String> authors = books.stream().map(BookResult::getAuthor).distinct().collect(Collectors.toList());

        if(categories.isEmpty()) {
            categories = categoryRepository.findAll();
        }

        if(authors.isEmpty()) {
            return bookRepository.findAllByPublishedAfterAndCategoriesInOrderByViewsDesc(
                    query.getPublishedAfter(),
                    new HashSet<>(categories)
            ).stream().map(this::bookToResult).collect(Collectors.toList());
        }

        return bookRepository.findAllByPublishedAfterAndCategoriesInAndAuthorInOrderByViewsDesc(
                query.getPublishedAfter(),
                new HashSet<>(categories),
                new HashSet<>(authors)
        ).stream().map(this::bookToResult).collect(Collectors.toList());
    }

    public BookResult getBook(String id) {
        Book book = findBookById(id);

        book.setViews(book.getViews() + 1);

        bookRepository.save(book);

        return bookToResult(book);
    }

    public List<BookResult> findByTitle(String queryString) {
        return bookRepository.findAllByTitleContains(queryString).stream().map(this::bookToResult).collect(Collectors.toList());
    }

    public BookResult addBook(CreateBookMessage message) {
        Book book = new Book();

        List<Category> categories = categoryRepository.findAllByIdIn(new HashSet<>(message.getCategoryIds()));

        book.setAuthor(message.getAuthor());
        book.setImageUrl(message.getImageUrl());
        book.setPageNumber(message.getPageNumber());
        book.setPublished(message.getPublished());
        book.setPublisher(message.getPublisher());
        book.setSold(message.getSold());
        book.setSummary(message.getSummary());
        book.setTitle(message.getTitle());
        book.setViews(message.getViews());
        book.setCategories(categories);

        Book createdBook = bookRepository.save(book);

        return bookToResult(createdBook);
    }


    public void deleteBook(String bookId) {
        Book book = findBookById(bookId);

        bookRepository.delete(book);
    }

    public BookResult updateBook(UpdateBookMessage message) {
        Book book = findBookById(message.getId());

        List<Category> categories = categoryRepository.findAllByIdIn(new HashSet<>(message.getCategoryIds()));
        categories.addAll(book.getCategories());

        book.setAuthor(message.getAuthor());
        book.setImageUrl(message.getImageUrl());
        book.setPageNumber(message.getPageNumber());
        book.setPublished(message.getPublished());
        book.setPublisher(message.getPublisher());
        book.setSold(message.getSold());
        book.setSummary(message.getSummary());
        book.setTitle(message.getTitle());
        book.setCategories(categories.stream().filter(distinctByKey(Category::getId)).collect(Collectors.toList()));

        bookRepository.save(book);

        return bookToResult(book);
    }

    public Book findBookById(String bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if(!book.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        return book.get();
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
                .averageRating(book.getAverageRating())
                .categories(book.getCategories().stream().map(this::categoryEntityToResult).collect(Collectors.toList()))
                .build();
    }

    private CategoryResult categoryEntityToResult(Category category) {
        return CategoryResult.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
