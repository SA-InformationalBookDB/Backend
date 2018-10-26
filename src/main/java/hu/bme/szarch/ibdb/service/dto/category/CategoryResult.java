package hu.bme.szarch.ibdb.service.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CategoryResult {

    private String id;

    private String name;

}
