package hu.bme.szarch.ibdb.controller.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Builder
@Getter
@Setter
public class UpdateUserRequest {

    private String email;
    private String nickname;
    private OffsetDateTime birthDate;

}
