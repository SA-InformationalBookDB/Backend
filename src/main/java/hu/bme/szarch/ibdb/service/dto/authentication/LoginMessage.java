package hu.bme.szarch.ibdb.service.dto.authentication;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginMessage {

    private String email;

    private String password;

    private String clientId;

    private String redirectUri;

}
