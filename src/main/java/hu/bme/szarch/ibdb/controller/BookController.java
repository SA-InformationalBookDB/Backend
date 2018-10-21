package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import hu.bme.szarch.ibdb.controller.dto.review.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @GetMapping("/popular")
    public Page<BookResponse> getPopularBooks(@RequestAttribute int page, @RequestAttribute int size) {
        return Page.empty();
    }

    @GetMapping("/bestseller")
    public Page<BookResponse> getBestSellerBooks(@RequestAttribute int page, @RequestAttribute int size) {
        return Page.empty();
    }

    @GetMapping("/offer")
    public Page<BookResponse> getOfferedBooks(@RequestAttribute int page, @RequestAttribute int size) {
        return Page.empty();
    }

    @GetMapping("/trending")
    public Page<BookResponse> getTrendingBooks(@RequestAttribute int page, @RequestAttribute int size) {
        return Page.empty();
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable String id) {
        return new BookResponse();
    }

    @GetMapping("/{id}/review")
    public Page<ReviewResponse> getBookReviews(@PathVariable String id, @RequestAttribute int page, @RequestAttribute int size) {
        return Page.empty();
    }

    @GetMapping("/find")
    public Page<BookResponse> findBooks(@RequestAttribute int page, @RequestAttribute int size, @RequestAttribute String bookName) {
        return Page.empty();
    }

    @PostMapping("/{id}/review")
    public void reviewBook(@PathVariable String id) {

    }

}
