package hu.bme.szarch.ibdb.controller;


import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import hu.bme.szarch.ibdb.controller.dto.book.CreateBookRequest;
import hu.bme.szarch.ibdb.controller.dto.book.UpdateBookRequest;
import hu.bme.szarch.ibdb.service.BookService;
import hu.bme.szarch.ibdb.service.ReviewService;
import hu.bme.szarch.ibdb.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private BookService bookService;
    private UserService userService;
    private ReviewService reviewService;

    public AdminController(BookService bookService, UserService userService, ReviewService reviewService) {
        this.bookService = bookService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @DeleteMapping("/{userId}/remove")
    public void removeUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("/{userId}/enable")
    public void enableUser(@PathVariable String userId) {
        userService.enableOrDisableUser(userId, true);
    }

    @PostMapping("/{userId}/disable")
    public void disableUser(@PathVariable String userId) {
        userService.enableOrDisableUser(userId, false);
    }

    @PostMapping("/book")
    public BookResponse addBook(@RequestBody CreateBookRequest request) {
        return DtoMapper.resultToResponse(bookService.addBook(DtoMapper.requestToMessage(request)));
    }

    @DeleteMapping("/book/{id}")
    public void removeBook(@PathVariable String id) {
        bookService.deleteBook(id);
    }

    @PutMapping("/book/{id}")
    public BookResponse updateBook(@PathVariable("id") String bookId, @RequestBody UpdateBookRequest request) {
        return DtoMapper.resultToResponse(bookService.updateBook(DtoMapper.requestToMessage(bookId, request)));
    }

    @DeleteMapping("/{userId}/review/{reviewId}")
    public void removeReview(@PathVariable String userId,
                             @PathVariable String reviewId) {
        reviewService.deleteReview(userId, reviewId);
    }

}
