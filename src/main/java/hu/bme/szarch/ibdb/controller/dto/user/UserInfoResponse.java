package hu.bme.szarch.ibdb.controller.dto.user;

import hu.bme.szarch.ibdb.domain.Role;
import lombok.Builder;

import java.time.OffsetDateTime;


@Builder
public class UserInfoResponse {

    private String id;
    private String email;
    private Role role;
    private String nickname;
    private OffsetDateTime birthDate;

}
