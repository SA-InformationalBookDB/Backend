package hu.bme.szarch.ibdb.controller.dto.book;

import lombok.*;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
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
