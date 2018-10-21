package hu.bme.szarch.ibdb.controller;


import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @DeleteMapping("/{userId}/remove")
    public void removeUser(@PathVariable String userId) {

    }

    @PostMapping("/{userId}/enable")
    public void enableUser(@PathVariable String userId) {

    }

    @PostMapping("/{userId}/disable")
    public void disableUser(@PathVariable String userId) {

    }

    @PostMapping("/book")
    public BookResponse addBook() {
        return new BookResponse();
    }

    @DeleteMapping("/book/{id}")
    public void removeBook(@PathVariable String id) {

    }

    @PutMapping("/book/{id}")
    public BookResponse updateBook(@PathVariable String id) {
        return new BookResponse();
    }

    @DeleteMapping("/{userId}/review")
    public void removeReview(@PathVariable String userId) {

    }

}
