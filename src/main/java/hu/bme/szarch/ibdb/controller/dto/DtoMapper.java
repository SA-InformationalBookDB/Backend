package hu.bme.szarch.ibdb.controller.dto;

import hu.bme.szarch.ibdb.controller.dto.oauth.LoginRequest;
import hu.bme.szarch.ibdb.controller.dto.oauth.LoginResponse;
import hu.bme.szarch.ibdb.controller.dto.oauth.RegistrationRequest;
import hu.bme.szarch.ibdb.controller.dto.oauth.RegistrationResponse;
import hu.bme.szarch.ibdb.service.dto.oauth.LoginMessage;
import hu.bme.szarch.ibdb.service.dto.oauth.LoginResult;
import hu.bme.szarch.ibdb.service.dto.oauth.RegistrationMessage;
import hu.bme.szarch.ibdb.service.dto.oauth.RegistrationResult;

public class DtoMapper {

    public static RegistrationMessage requestToMessage(RegistrationRequest request) {
        return RegistrationMessage.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public static RegistrationResponse resultToResponse(RegistrationResult result) {
        return RegistrationResponse.builder()
                .userId(result.getUserId())
                .build();
    }

    public static LoginMessage requestToMessage(LoginRequest request) {
        return LoginMessage.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .clientId(request.getClientId())
                .redirectUri(request.getRedirectUri())
                .build();
    }

    public static LoginResponse resultToResponse(LoginResult result) {
        return LoginResponse.builder()
                .code(result.getCode())
                .redirectUri(result.getRedirectUri())
                .build();
    }

}
