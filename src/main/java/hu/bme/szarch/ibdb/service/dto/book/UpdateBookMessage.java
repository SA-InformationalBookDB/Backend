package hu.bme.szarch.ibdb.service.dto.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class UpdateBookMessage {

    private String id;

    private String title;

    private String author;

    private OffsetDateTime published;

    private String publisher;

    private String imageUrl;

    private String summary;

    private int pageNumber;

    private int sold;

    private List<String> categoryIds;

}
