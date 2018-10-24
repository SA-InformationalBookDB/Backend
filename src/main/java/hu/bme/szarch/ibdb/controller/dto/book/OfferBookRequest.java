package hu.bme.szarch.ibdb.controller.dto.book;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OfferBookRequest {

    private List<String> authors;

}
