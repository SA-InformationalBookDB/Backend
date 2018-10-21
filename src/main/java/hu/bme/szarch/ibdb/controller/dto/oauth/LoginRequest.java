package hu.bme.szarch.ibdb.controller.dto.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
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
