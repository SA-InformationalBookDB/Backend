package hu.bme.szarch.ibdb.domain.oauth;

import lombok.*;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class RefreshToken implements OAuth2RefreshToken {

    @Id
    private String value;

    @Override
    public String getValue() {
        return value;
    }
}
