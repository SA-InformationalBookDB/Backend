package hu.bme.szarch.ibdb.domain.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class RefreshToken {

    @Id
    private String value;

    private OffsetDateTime created;

    private String userId;

    @OneToOne(orphanRemoval = true)
    private AccessToken accessToken;

    @PrePersist
    public void onPrePersist() {
        this.created = OffsetDateTime.now();
    }


}
