package hu.bme.szarch.ibdb.service.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;


@Builder
@Setter
@Getter
public class UpdateUserMessage {

    private String id;

    private String email;

    private OffsetDateTime dateOfBirth;

    private String nickname;

}
