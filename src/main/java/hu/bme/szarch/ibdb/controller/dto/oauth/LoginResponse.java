package hu.bme.szarch.ibdb.controller.dto.oauth;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String code;

    private String redirectUri;

}
