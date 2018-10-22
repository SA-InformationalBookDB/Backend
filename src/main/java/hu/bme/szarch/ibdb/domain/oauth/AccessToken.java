package hu.bme.szarch.ibdb.domain.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class AccessToken {

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

}
