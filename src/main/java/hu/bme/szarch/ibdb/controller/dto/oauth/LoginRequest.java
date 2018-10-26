package hu.bme.szarch.ibdb.controller.dto.oauth;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String clientId;

    @NotNull
    private String redirectUri;

}
