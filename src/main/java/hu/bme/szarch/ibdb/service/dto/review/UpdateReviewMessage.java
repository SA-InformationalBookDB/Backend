package hu.bme.szarch.ibdb.service.dto.review;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UpdateReviewMessage {

    private String userId;
    private String reviewId;
    private int points;
    private String comment;

}
