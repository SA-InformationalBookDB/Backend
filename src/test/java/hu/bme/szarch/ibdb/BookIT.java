package hu.bme.szarch.ibdb;


import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import hu.bme.szarch.ibdb.helper.IbdbPageImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = IbdbApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class BookIT extends TestBase {

    @Test
    public void getPopularBooks() {
        List<String> ids = withManyBooks(50);

        Page<BookResponse> popularBooks = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/book/popular")
                        .queryParam("size", 25)
                        .queryParam("page", 2)
                        .build()
                )
                .header(authorizationHeader, userAccessToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<IbdbPageImpl<BookResponse>>() {
                }).returnResult().getResponseBody();

        assertNotNull(popularBooks);
        assertEquals(25, popularBooks.getContent().size());
        assertEquals(2, popularBooks.getNumber());
    }


}
