package hu.bme.szarch.ibdb.controller.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CategoryResponse {

    private String id;
    private String name;

}
