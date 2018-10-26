package hu.bme.szarch.ibdb.controller.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoriesUpdateRequest {

    private List<String> categoryIds;

}
