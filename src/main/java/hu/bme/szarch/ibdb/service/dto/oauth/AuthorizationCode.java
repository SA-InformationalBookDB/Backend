package hu.bme.szarch.ibdb.service.dto.oauth;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
public class AuthorizationCode {

    @Id
    private String code;

    private String userId;

    private String clientId;

    private String redirectUri;

    private OffsetDateTime expirationDate;


}
