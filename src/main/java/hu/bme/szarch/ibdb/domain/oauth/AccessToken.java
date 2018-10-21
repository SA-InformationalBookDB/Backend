package hu.bme.szarch.ibdb.domain.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import java.time.OffsetDateTime;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class AccessToken implements OAuth2AccessToken {

    @Id
    private String value;

    private OffsetDateTime expirationDate;

    private String userId;

    private OffsetDateTime created;

    @OneToOne
    private RefreshToken refreshToken;

    @PrePersist
    public void onPrePersist() {
        this.created = OffsetDateTime.now();
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return new HashMap<>();
    }

    @Override
    public Set<String> getScope() {
        return new HashSet<>();
    }

    @Override
    public OAuth2RefreshToken getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String getTokenType() {
        return "access_token";
    }

    @Override
    public boolean isExpired() {
        return expirationDate.isBefore(OffsetDateTime.now());
    }

    @Override
    public Date getExpiration() {
        return Date.from(expirationDate.toInstant());
    }

    @Override
    public int getExpiresIn() {
        return OffsetDateTime.now().getSecond() - expirationDate.getSecond();
    }

    @Override
    public String getValue() {
        return value;
    }
}
