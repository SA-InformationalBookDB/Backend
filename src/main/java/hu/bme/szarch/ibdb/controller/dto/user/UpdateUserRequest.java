package hu.bme.szarch.ibdb.controller.dto.user;

import lombok.*;

import java.time.OffsetDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    private String email;
    private String nickname;
    private OffsetDateTime birthDate;

}
