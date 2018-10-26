package hu.bme.szarch.ibdb.service.dto.user;

import hu.bme.szarch.ibdb.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Builder
@Setter
@Getter
public class UserInfoResult {

    private String id;
    private Role role;
    private OffsetDateTime dateOfBirth;
    private String email;

}
