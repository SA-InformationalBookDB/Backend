package hu.bme.szarch.ibdb.service.dto.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class OfferedBookQuery {

    private Pageable pageable;
    private OffsetDateTime publishedAfter;
    private List<String> authors;
    private String userId;

}
