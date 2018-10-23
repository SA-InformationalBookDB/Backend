package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.domain.Role;
import hu.bme.szarch.ibdb.domain.User;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.UserRepository;
import hu.bme.szarch.ibdb.service.dto.authorization.AuthorizationCodeMessage;
import hu.bme.szarch.ibdb.service.dto.authentication.*;
import hu.bme.szarch.ibdb.service.dto.token.AccessTokenResult;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthenticationService extends TokenGenerator {

    private UserRepository userRepository;
    private AuthorizationCodeService authorizationCodeService;
    private TokenService tokenService;
    private PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository,
                                 AuthorizationCodeService authorizationCodeService,
                                 TokenService tokenService) {
        this.userRepository = userRepository;
        this.authorizationCodeService = authorizationCodeService;
        this.tokenService = tokenService;
        this.passwordEncoder = getEncoder();
    }

    public RegistrationResult register(RegistrationMessage message) {
        Optional<User> foundUser = userRepository.findByEmail(message.getEmail());

        if(foundUser.isPresent()) {
            throw new ServerException(Errors.INVALID_REGISTRATION);
        }

        User user = new User();

        user.setEmail(message.getEmail());
        user.setPassword(passwordEncoder.encode(message.getPassword()));
        user.setCategories(Collections.emptyList());
        user.setRole(Role.USER);
        user.setReviews(Collections.emptyList());
        user.setFavourites(Collections.emptyList());

        user = userRepository.save(user);

        return RegistrationResult.builder()
                .userId(user.getId())
                .build();
    }

    public LoginResult login(LoginMessage message) {
        Optional<User> user = userRepository.findByEmail(message.getEmail());

        if(!user.isPresent() || !this.passwordEncoder.matches(message.getPassword(), user.get().getPassword())) {
            throw new ServerException(Errors.INVALID_LOGIN_EXCEPTION);
        }

        return LoginResult.builder()
                .code(authorizationCodeService.createAuthorizationCode(user.get().getId(), message.getClientId(), message.getRedirectUri()))
                .redirectUri(message.getRedirectUri())
                .build();
    }

    public void logout(String userId) {
        if(userId == null) {
            throw new ServerException(Errors.UNABLE_TO_LOUGOUT);
        }

        tokenService.deleteUserTokens(userId);
    }

    public AccessTokenResult exchangeAuthorizationCode(AuthorizationCodeMessage message) {
        //TODO: check client userId

        String userId = authorizationCodeService.consumeAuthorizationCode(message.getCode(), message.getClientId(), message.getRedirectUri());

        return tokenService.createAccessToken(userId);
    }

    public AccessTokenResult refreshAccessToken(String refreshToken) {
        return tokenService.refreshAccessToken(refreshToken);
    }

    @Bean
    private PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

}
