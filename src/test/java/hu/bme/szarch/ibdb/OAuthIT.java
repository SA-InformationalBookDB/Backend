package hu.bme.szarch.ibdb;


import hu.bme.szarch.ibdb.controller.dto.oauth.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = IbdbApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class OAuthIT extends TestBase {

    @Test
    public void registration() {

        RegistrationRequest request = RegistrationRequest.builder()
                .email("test1@test.test")
                .password("Asdqwe123")
                .build();

        client.post()
                .uri("/oauth/register")
                .syncBody(request)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegistrationResponse.class);

    }

    @Test
    public void login() {
        withOneUser(RegistrationRequest.builder().email("test2@test.test").password("Asdqwe123").build());

        LoginRequest request = LoginRequest.builder()
                .email("test2@test.test")
                .password("Asdqwe123")
                .clientId("73281642736")
                .redirectUri("http://localhost/login")
                .build();

        LoginResponse response = client.post()
                .uri("/oauth/login")
                .syncBody(request)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LoginResponse.class).returnResult().getResponseBody();

        client.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("redirect_uri", "http://localhost/login")
                        .queryParam("client_id", "73281642736")
                        .queryParam("code", response.getCode())
                        .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccessTokenResponse.class);

    }

}
