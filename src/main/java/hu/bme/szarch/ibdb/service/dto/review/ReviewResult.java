package hu.bme.szarch.ibdb.service.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Builder
@Getter
@Setter
public class ReviewResult {

    private String id;

    private String bookId;

    private float points;

    private OffsetDateTime date;

    private String comment;

    private String userNickname;

}
