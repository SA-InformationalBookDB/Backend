package hu.bme.szarch.ibdb.controller.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CategoriesUpdateRequest {

    @NotNull
    private List<String> categoryIds;

}
