package hu.bme.szarch.ibdb.controller.dto.book;

import hu.bme.szarch.ibdb.controller.dto.user.CategoryResponse;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BookResponse {

    private String id;
    private String title;
    private String author;
    private OffsetDateTime published;
    private String publisher;
    private String imageUrl;
    private String summary;
    private int pageNumber;
    private int sold;
    private int views;
    private double averageRating;
    private List<CategoryResponse> categories;
    private boolean isFavourite;

}
