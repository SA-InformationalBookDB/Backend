package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.book.BookRequest;
import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import hu.bme.szarch.ibdb.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/book")
public class AdminBookController {

    private BookService bookService;

    public AdminBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public BookResponse addBook(@RequestBody @Valid BookRequest request) {
        return DtoMapper.bookResultToResponse(bookService.addBook(DtoMapper.createBookRequestToMessage(request)));
    }

    @DeleteMapping("/{id}")
    public void removeBook(@PathVariable String id) {
        bookService.deleteBook(id);
    }

    @PutMapping("/{id}")
    public BookResponse updateBook(@PathVariable("id") String bookId, @RequestBody @Valid BookRequest request) {
        return DtoMapper.bookResultToResponse(bookService.updateBook(DtoMapper.updateBookRequestToMessage(bookId, request)));
    }

}
