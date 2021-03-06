package hu.bme.szarch.ibdb.service.dto.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Builder
@Getter
@Setter
public class OfferedBookQuery {

    private OffsetDateTime publishedAfter;
    private String userId;

}
