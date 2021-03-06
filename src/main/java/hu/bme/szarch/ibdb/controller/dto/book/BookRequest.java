package hu.bme.szarch.ibdb.controller.dto.book;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BookRequest {

    @NotNull
    private String title;

    @NotNull
    private String author;

    private OffsetDateTime published;

    private String publisher;

    private String imageUrl;

    @NotNull
    private String summary;

    @NotNull
    private int pageNumber;

    private int sold;

    private List<String> categoryIds = new ArrayList<>();

}
