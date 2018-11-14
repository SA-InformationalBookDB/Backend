package hu.bme.szarch.ibdb.controller.dto.user;

import hu.bme.szarch.ibdb.domain.Role;
import lombok.*;

import java.time.OffsetDateTime;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    private String id;
    private String email;
    private Role role;
    private String nickname;
    private OffsetDateTime birthDate;

}
