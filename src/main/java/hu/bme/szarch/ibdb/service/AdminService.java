package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Service
public class AdminService {

    public AdminService() {
    }

    public void removeUser() {

    }


    public void enableUser() {

    }


    public void disableUser() {

    }

    public BookResponse addBook() {
        return new BookResponse();
    }


    public void removeBook() {

    }

    public BookResponse updateBook() {
        return new BookResponse();
    }

}
