package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.domain.Roles;
import hu.bme.szarch.ibdb.domain.User;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.UserRepository;
import hu.bme.szarch.ibdb.service.dto.oauth.LoginMessage;
import hu.bme.szarch.ibdb.service.dto.oauth.LoginResult;
import hu.bme.szarch.ibdb.service.dto.oauth.RegistrationMessage;
import hu.bme.szarch.ibdb.service.dto.oauth.RegistrationResult;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class OAuthService extends TokenGenerator {

    private UserRepository userRepository;
    private AuthorizationCodeService authorizationCodeService;

    public OAuthService(UserRepository userRepository,
                        AuthorizationCodeService authorizationCodeService) {
        this.userRepository = userRepository;
        this.authorizationCodeService = authorizationCodeService;
    }

    public RegistrationResult register(RegistrationMessage message) {
        User user = new User();

        user.setEmail(message.getEmail());
        user.setPassword(getEncoder().encode(message.getPassword()));
        user.setCategories(Collections.emptyList());
        user.setRole(Roles.USER);
        user.setReviews(Collections.emptyList());
        user.setFavourites(Collections.emptyList());

        user = userRepository.save(user);

        return RegistrationResult.builder()
                .userId(user.getId())
                .build();
    }

    public LoginResult login(LoginMessage message) {
        Optional<User> user = userRepository.findByEmailAndPassword(message.getEmail(), message.getPassword());

        if(!user.isPresent()) {
            throw new ServerException(Errors.INVALID_LOGIN_EXCEPTION);
        }

        return LoginResult.builder()
                .code(authorizationCodeService.createAuthorizationCode(user.get().getId(), message.getClientId(), message.getRedirectUri()).getCode())
                .redirectUri(message.getRedirectUri())
                .build();
    }

    @Bean
    private PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

}
