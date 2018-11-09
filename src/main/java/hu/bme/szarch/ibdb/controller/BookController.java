package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import hu.bme.szarch.ibdb.controller.dto.book.OfferBookRequest;
import hu.bme.szarch.ibdb.filter.AuthenticationFilter;
import hu.bme.szarch.ibdb.filter.dto.UserInfo;
import hu.bme.szarch.ibdb.service.BookService;
import hu.bme.szarch.ibdb.service.ReviewService;
import hu.bme.szarch.ibdb.service.dto.book.OfferedBookQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookService bookService;
    private ReviewService reviewService;

    public BookController(BookService bookService, ReviewService reviewService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
    }

    @GetMapping("/popular")
    public Page<BookResponse> getPopularBooks(@RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "10") int size) {
        return DtoMapper.bookResultsToResponse(bookService.getPopulars(PageRequest.of(page, size)));
    }

    @GetMapping("/bestseller")
    public Page<BookResponse> getBestSellerBooks(@RequestParam(required = false, defaultValue = "0") int page,
                                                 @RequestParam(required = false, defaultValue = "10") int size) {
        return DtoMapper.bookResultsToResponse(bookService.getBestSellers(PageRequest.of(page, size)));
    }

    @PostMapping("/offer")
    public Page<BookResponse> getOfferedBooks(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                                              @RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "10") int size,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime publishedAfter,
                                              @RequestBody OfferBookRequest request) {
        return DtoMapper.bookResultsToResponse(bookService.findOffered(OfferedBookQuery.builder()
                .pageable(PageRequest.of(page, size))
                .authors(request.getAuthors())
                .publishedAfter(publishedAfter)
                .userId(userInfo.getUserId())
                .build()
        ));
    }

    @GetMapping("/trending")
    public Page<BookResponse> getTrendingBooks(@RequestParam(required = false, defaultValue = "0") int page,
                                               @RequestParam(required = false, defaultValue = "10") int size,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime publishedAfter) {
        return DtoMapper.bookResultsToResponse(bookService.getTrending(PageRequest.of(page, size), publishedAfter));
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable String id) {
        return DtoMapper.bookResultToResponse(bookService.getBook(id));
    }

    @PostMapping("/find")
    public Page<BookResponse> findBooks(@RequestParam(required = false, defaultValue = "0") int page,
                                        @RequestParam(required = false, defaultValue = "10") int size,
                                        @RequestParam String queryString) {
        return DtoMapper.bookResultsToResponse(bookService.findByTitle(PageRequest.of(page, size), queryString));
    }
}
