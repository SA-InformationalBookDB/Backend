package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import hu.bme.szarch.ibdb.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    private BookService bookService;

    public PublicController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book/popular")
    public List<BookResponse> getPopularBooks() {
        return DtoMapper.bookResultsToResponse(bookService.getPopulars(PageRequest.of(0, 10))).getContent();
    }

    @GetMapping("/book/bestseller")
    public Page<BookResponse> getBestSellerBooks() {
        return DtoMapper.bookResultsToResponse(bookService.getBestSellers(PageRequest.of(0, 10)));
    }

    @GetMapping("/book/trending")
    public Page<BookResponse> getTrendingBooks() {
        return DtoMapper.bookResultsToResponse(bookService.getTrending(PageRequest.of(0, 10), OffsetDateTime.now().minusMonths(3)));
    }

}
