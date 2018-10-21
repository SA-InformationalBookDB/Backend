package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.oauth.LoginRequest;
import hu.bme.szarch.ibdb.controller.dto.oauth.LoginResponse;
import hu.bme.szarch.ibdb.controller.dto.oauth.RegistrationRequest;
import hu.bme.szarch.ibdb.controller.dto.oauth.RegistrationResponse;
import hu.bme.szarch.ibdb.service.OAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static hu.bme.szarch.ibdb.controller.dto.DtoMapper.requestToMessage;
import static hu.bme.szarch.ibdb.controller.dto.DtoMapper.resultToResponse;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    private OAuthService oAuthService;

    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @PostMapping("/register")
    public RegistrationResponse register(@Valid @RequestBody RegistrationRequest request) {
        return resultToResponse(oAuthService.register(requestToMessage(request)));
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return resultToResponse(oAuthService.login(requestToMessage(request)));
    }

}
