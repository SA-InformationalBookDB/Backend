package hu.bme.szarch.ibdb.service.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class CategoriesUpdateMessage {

    private String userId;
    private List<String> categoryIds;

}
