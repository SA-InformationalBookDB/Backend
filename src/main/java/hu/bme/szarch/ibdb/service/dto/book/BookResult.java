package hu.bme.szarch.ibdb.service.dto.book;

import hu.bme.szarch.ibdb.service.dto.category.CategoryResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class BookResult {

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

    private List<CategoryResult> categories;

}
