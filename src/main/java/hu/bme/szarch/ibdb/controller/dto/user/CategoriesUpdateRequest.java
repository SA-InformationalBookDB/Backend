package hu.bme.szarch.ibdb.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesUpdateRequest {

    @NotNull
    private List<String> categoryIds = new ArrayList<>();

}
