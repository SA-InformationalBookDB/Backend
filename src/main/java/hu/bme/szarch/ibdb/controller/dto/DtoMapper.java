package hu.bme.szarch.ibdb.controller.dto;

import hu.bme.szarch.ibdb.controller.dto.oauth.*;
import hu.bme.szarch.ibdb.service.dto.authentication.LoginMessage;
import hu.bme.szarch.ibdb.service.dto.authentication.LoginResult;
import hu.bme.szarch.ibdb.service.dto.authentication.RegistrationMessage;
import hu.bme.szarch.ibdb.service.dto.authentication.RegistrationResult;
import hu.bme.szarch.ibdb.service.dto.token.AccessTokenResult;

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

    public static AccessTokenResponse resultToResponse(AccessTokenResult result) {
        return AccessTokenResponse.builder()
                .accessToken(result.getAccessToken())
                .accessTokenExpiration(result.getAccessTokenExpiration())
                .refreshToken(result.getRefreshToken())
                .build();
    }

}
