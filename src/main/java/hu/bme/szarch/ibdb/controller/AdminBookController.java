package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.book.BookRequest;
import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import hu.bme.szarch.ibdb.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/book")
public class AdminBookController {

    private BookService bookService;

    public AdminBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public BookResponse addBook(@RequestBody BookRequest request) {
        return DtoMapper.resultToResponse(bookService.addBook(DtoMapper.requestToMessage(request)));
    }

    @DeleteMapping("/{id}")
    public void removeBook(@PathVariable String id) {
        bookService.deleteBook(id);
    }

    @PutMapping("/{id}")
    public BookResponse updateBook(@PathVariable("id") String bookId, @RequestBody BookRequest request) {
        return DtoMapper.resultToResponse(bookService.updateBook(DtoMapper.requestToMessage(bookId, request)));
    }

}
