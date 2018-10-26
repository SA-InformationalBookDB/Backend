package hu.bme.szarch.ibdb.service.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class FavouriteMessage {

    private String userId;
    private String bookId;

}
