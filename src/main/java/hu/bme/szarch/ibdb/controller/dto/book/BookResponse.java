package hu.bme.szarch.ibdb.controller.dto.book;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BookResponse {

    private String id;
    private String title;
    private String author;
    private String published;
    private String publisher;
    private String imageUrl;
    private String summary;
    private int pageNumber;
    private int sold;
    private int views;

}
