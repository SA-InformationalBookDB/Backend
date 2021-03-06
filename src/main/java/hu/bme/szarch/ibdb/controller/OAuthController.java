package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.oauth.*;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.service.AuthenticationService;
import hu.bme.szarch.ibdb.service.dto.authorization.AuthorizationCodeMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
@RequestMapping("/oauth")
public class OAuthController extends WebBase {

    private AuthenticationService authenticationService;

    public OAuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public RegistrationResponse register(@Valid @RequestBody RegistrationRequest request) {
        return DtoMapper.registrationResultToResponse(authenticationService.register(DtoMapper.registrationRequestToMessage(request)));
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return DtoMapper.loginResultToResponse(authenticationService.login(DtoMapper.loginRequestToMessage(request)));
    }

    @PostMapping("/token")
    @ApiOperation("Require access-token or refresh existing one. (grant_type = authorization_code|refresh_token)")
    public AccessTokenResponse token(
            @RequestParam(value = "grant_type") String grantType,
            @RequestParam(value = "client_id", defaultValue = "") String clientId,
            @RequestParam(value = "redirect_uri", required = false) String redirectUri,
            @RequestParam(value = "code", required = false, defaultValue = "") String code,
            @RequestParam(value = "refresh_token", required = false, defaultValue = "") String refreshToken

    ) {
        if (grantType.equals("authorization_code") && !code.isEmpty()) {
            return DtoMapper.accessTokenResultToResponse(authenticationService.exchangeAuthorizationCode(AuthorizationCodeMessage.builder()
                    .clientId(clientId)
                    .code(code)
                    .redirectUri(redirectUri)
                    .build()));
        } else if (grantType.equals("refresh_token") && !refreshToken.isEmpty()) {
           return DtoMapper.accessTokenResultToResponse(authenticationService.refreshAccessToken(refreshToken));
        }

        throw new ServerException(Errors.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String accessToken) {
        authenticationService.logout(accessToken);
    }

    @GetMapping("/authorize")
    public RedirectView authorize(
            @RequestParam("response_type") String responseType,
            @RequestParam("client_id") String clientId,
            @RequestParam(value = "redirect_uri", required = false) String redirectUri,
            RedirectAttributes redirectAttributes
    ) {
        if (responseType.equals("code") /*&& this.clientService.checkClientId(clientId)*/) {
            redirectAttributes.addAttribute("client_id", clientId);
            redirectAttributes.addAttribute("redirect_uri", redirectUri);
            return new RedirectView("http://localhost/login");
        }
        redirectAttributes.addAttribute("error", "invalid_request");
        return new RedirectView(redirectUri);
    }


}
