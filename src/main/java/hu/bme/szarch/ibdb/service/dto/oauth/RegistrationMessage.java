package hu.bme.szarch.ibdb.service.dto.oauth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegistrationMessage {

    private String email;

    private String password;

}
