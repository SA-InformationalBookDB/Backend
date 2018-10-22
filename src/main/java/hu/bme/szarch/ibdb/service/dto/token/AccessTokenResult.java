package hu.bme.szarch.ibdb.service.dto.token;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Builder
@Setter
@Getter
public class AccessTokenResult {

    private String accessToken;
    private OffsetDateTime accessTokenExpiration;
    private String refreshToken;

}
