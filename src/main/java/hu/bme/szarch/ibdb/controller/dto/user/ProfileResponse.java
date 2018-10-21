package hu.bme.szarch.ibdb.controller.dto.user;

import lombok.Builder;


@Builder
public class ProfileResponse {

    private String id;
    private String email;
    private String role;
    private String birthDate;
    private boolean isValid;

}
