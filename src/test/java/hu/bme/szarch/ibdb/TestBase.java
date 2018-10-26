package hu.bme.szarch.ibdb;

import hu.bme.szarch.ibdb.controller.dto.oauth.RegistrationRequest;
import hu.bme.szarch.ibdb.controller.dto.oauth.RegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

public class TestBase {

    private int serialNumber = 0;

    @LocalServerPort
    private int localServerPort;

    @Autowired
    protected WebTestClient client;

    protected String withOneUser(RegistrationRequest request) {
        RegistrationResponse response = client.post()
                .uri("/oauth/register")
                .syncBody(request)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegistrationResponse.class).returnResult().getResponseBody();



        return response.getUserId();
    }

}
