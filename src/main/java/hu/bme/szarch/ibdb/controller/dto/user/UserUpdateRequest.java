package hu.bme.szarch.ibdb.controller.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Builder
@Getter
@Setter
public class UserUpdateRequest {

    private String email;
    private OffsetDateTime birthDate;

}
