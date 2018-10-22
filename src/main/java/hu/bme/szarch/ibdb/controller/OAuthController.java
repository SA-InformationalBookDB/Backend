package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.oauth.*;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.service.AuthenticationService;
import hu.bme.szarch.ibdb.service.dto.authorization.AuthorizationCodeMessage;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static hu.bme.szarch.ibdb.controller.dto.DtoMapper.requestToMessage;
import static hu.bme.szarch.ibdb.controller.dto.DtoMapper.resultToResponse;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    private AuthenticationService authenticationService;

    public OAuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public RegistrationResponse register(@Valid @RequestBody RegistrationRequest request) {
        return resultToResponse(authenticationService.register(requestToMessage(request)));
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return resultToResponse(authenticationService.login(requestToMessage(request)));
    }

    @PostMapping("/token")
    public AccessTokenResponse token(
            @RequestParam(value = "grant_type") String grantType,
            @RequestParam(value = "client_id", defaultValue = "") String clientId,
            @RequestParam(value = "redirect_uri", required = false) String redirectUri,
            @RequestParam(value = "code", required = false, defaultValue = "") String code,
            @RequestParam(value = "refresh_token", required = false, defaultValue = "") String refreshToken

    ) {
        if (grantType.equals("authorization_code") && !code.isEmpty()) {
            return DtoMapper.resultToResponse(authenticationService.exchangeAuthorizationCode(AuthorizationCodeMessage.builder()
                    .clientId(clientId)
                    .code(code)
                    .redirectUri(redirectUri)
                    .build()));
        } else if (grantType.equals("refresh_token") && !refreshToken.isEmpty()) {
           return DtoMapper.resultToResponse(authenticationService.refreshAccessToken(refreshToken));
        }

        throw new ServerException(Errors.BAD_REQUEST);
    }

}
