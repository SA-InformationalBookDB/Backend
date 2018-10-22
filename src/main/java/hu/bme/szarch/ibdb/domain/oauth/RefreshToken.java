package hu.bme.szarch.ibdb.domain.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class RefreshToken {

    @Id
    private String value;

    @OneToOne
    private AccessToken accessToken;

}
