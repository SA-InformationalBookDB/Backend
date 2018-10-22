package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import hu.bme.szarch.ibdb.controller.dto.review.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @GetMapping("/popular")
    public Page<BookResponse> getPopularBooks(@RequestParam int page, @RequestParam int size) {
        return Page.empty();
    }

    @GetMapping("/bestseller")
    public Page<BookResponse> getBestSellerBooks(@RequestParam int page, @RequestParam int size) {
        return Page.empty();
    }

    @GetMapping("/offer")
    public Page<BookResponse> getOfferedBooks(@RequestParam int page, @RequestParam int size) {
        return Page.empty();
    }

    @GetMapping("/trending")
    public Page<BookResponse> getTrendingBooks(@RequestParam(required = false) int page, @RequestParam(required = false) int size) {
        return Page.empty();
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable String id) {
        return new BookResponse();
    }

    @GetMapping("/{id}/review")
    public Page<ReviewResponse> getBookReviews(@PathVariable String id, @RequestParam int page, @RequestParam int size) {
        return Page.empty();
    }

    @GetMapping("/find")
    public Page<BookResponse> findBooks(@RequestParam int page, @RequestParam int size, @RequestParam String bookName) {
        return Page.empty();
    }

    @PostMapping("/{id}/review")
    public void reviewBook(@PathVariable String id) {

    }

}
