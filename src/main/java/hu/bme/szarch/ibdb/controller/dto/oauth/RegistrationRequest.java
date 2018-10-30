package hu.bme.szarch.ibdb.controller.dto.oauth;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class RegistrationRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;

}
