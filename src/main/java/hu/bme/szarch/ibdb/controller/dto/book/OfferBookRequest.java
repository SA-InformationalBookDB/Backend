package hu.bme.szarch.ibdb.controller.dto.book;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class OfferBookRequest {

    @NotNull
    private List<String> authors;

}
