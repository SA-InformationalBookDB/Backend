package hu.bme.szarch.ibdb;

import hu.bme.szarch.ibdb.controller.dto.book.BookRequest;
import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import hu.bme.szarch.ibdb.controller.dto.oauth.RegistrationRequest;
import hu.bme.szarch.ibdb.controller.dto.oauth.RegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestBase {

    private int serialNumber = 0;

    protected final String authorizationHeader = "Authorization";
    protected final String adminAccessToken = "crXB8Du8N7v8NbPI9O4IvfLayIgWddJ0iNvof7Decsn8BqlbbvJ5KTCy3zmPw5Rq";
    protected final String userAccessToken = "crXB8Du8N7v8NbPI9O4IvfLayIgWddJ0iNvof7Decsn8BqlbbvJ5KTCy3zmPw5Rw";

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

        assertNotNull(response);

        return response.getUserId();
    }

    protected List<String> withManyBooks(int amount) {
        List<String> ids = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            BookResponse response = client.post()
                    .uri("/admin/book")
                    .header(authorizationHeader, adminAccessToken)
                    .syncBody(getNewBookRequest())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(BookResponse.class).returnResult().getResponseBody();

            assertNotNull(response);

            ids.add(response.getId());
        }

        return ids;
    }

    protected BookRequest getNewBookRequest() {
        BookRequest bookRequest = BookRequest.builder()
                .author("author_"+serialNumber)
                .imageUrl("image_url_"+serialNumber)
                .pageNumber(serialNumber)
                .published(OffsetDateTime.now())
                .publisher("publisher_"+serialNumber)
                .sold(serialNumber)
                .summary("summary_"+serialNumber)
                .title("title_"+serialNumber)
                .views(serialNumber)
                .build();

        serialNumber++;

        return bookRequest;
    }

}
