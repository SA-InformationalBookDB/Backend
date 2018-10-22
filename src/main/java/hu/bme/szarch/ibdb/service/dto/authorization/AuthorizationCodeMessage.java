package hu.bme.szarch.ibdb.service.dto.authorization;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthorizationCodeMessage {

    private String code;

    private String clientId;

    private String redirectUri;

}
