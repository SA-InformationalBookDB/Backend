package hu.bme.szarch.ibdb.controller.dto.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;

}
