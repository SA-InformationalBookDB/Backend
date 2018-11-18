package hu.bme.szarch.ibdb.service.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateReviewMessage {

    private String userId;
    private String bookId;
    private float points;
    private String comment;

}
