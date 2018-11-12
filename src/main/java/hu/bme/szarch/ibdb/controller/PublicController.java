package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import hu.bme.szarch.ibdb.service.BookService;
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
        return DtoMapper.bookResultsToResponse(bookService.getTopPopulars());
    }

    @GetMapping("/book/bestseller")
    public List<BookResponse> getBestSellerBooks() {
        return DtoMapper.bookResultsToResponse(bookService.getTopBestSellers());
    }

    @GetMapping("/book/trending")
    public List<BookResponse> getTrendingBooks() {
        return DtoMapper.bookResultsToResponse(bookService.getTrending(OffsetDateTime.now().minusMonths(3)));
    }

}
