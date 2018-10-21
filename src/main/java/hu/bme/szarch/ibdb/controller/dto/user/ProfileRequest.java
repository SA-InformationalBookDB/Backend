package hu.bme.szarch.ibdb.controller.dto.user;

import lombok.Builder;

@Builder
public class ProfileRequest {

    private String email;
    private String role;
    private String birthDate;
    private boolean isValid;

}
