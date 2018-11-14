package hu.bme.szarch.ibdb.controller.dto.user;

import hu.bme.szarch.ibdb.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.jmx.export.annotation.ManagedNotifications;

import java.time.OffsetDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    private String id;
    private String email;
    private Role role;
    private String nickname;
    private OffsetDateTime birthDate;

}
