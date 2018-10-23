package hu.bme.szarch.ibdb.controller.dto.book;

import lombok.*;

import java.time.OffsetDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {

    private String id;

    private int points;

    private OffsetDateTime date;

    private String comment;

}
