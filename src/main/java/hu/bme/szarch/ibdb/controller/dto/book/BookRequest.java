package hu.bme.szarch.ibdb.controller.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookRequest {

    private String title;

    private String author;

    private OffsetDateTime published;

    private String publisher;

    private String imageUrl;

    private String summary;

    private int pageNumber;

    private int sold;

    private int views;

}
