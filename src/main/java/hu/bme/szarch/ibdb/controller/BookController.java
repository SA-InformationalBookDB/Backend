package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import hu.bme.szarch.ibdb.service.BookService;
import hu.bme.szarch.ibdb.service.dto.book.OfferedBookQuery;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController extends WebBase {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/popular")
    public List<BookResponse> getPopularBooks() {
        return DtoMapper.bookResultsToResponse(bookService.getPopulars());
    }

    @GetMapping("/bestseller")
    public List<BookResponse> getBestSellerBooks() {
        return DtoMapper.bookResultsToResponse(bookService.getBestSellers());
    }

    @PostMapping("/offer")
    public List<BookResponse> getOfferedBooks(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime publishedAfter) {
        return DtoMapper.bookResultsToResponse(bookService.findOffered(OfferedBookQuery.builder()
                .publishedAfter(publishedAfter)
                .userId(getUserInfo().getUserId())
                .build()
        ));
    }

    @GetMapping("/trending")
    public List<BookResponse> getTrendingBooks(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime publishedAfter) {
        return DtoMapper.bookResultsToResponse(bookService.getTrending(publishedAfter));
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable String id) {
        return DtoMapper.bookResultToResponse(bookService.getBook(getUserInfo(), id));
    }

    @PostMapping("/find")
    public List<BookResponse> findBooks(@RequestParam String queryString) {
        return DtoMapper.bookResultsToResponse(bookService.findByTitle(queryString));
    }
}
