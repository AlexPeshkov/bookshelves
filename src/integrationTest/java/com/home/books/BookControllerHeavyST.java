package com.home.books;

import com.home.books.domain.Book;
import com.home.books.domain.NonFictionBook;
import com.home.books.domain.FictionBook;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
@TestPropertySource("classpath:application.properties")
public class BookControllerHeavyST {
    @Value("${server.port}") private int port;
    @Autowired private Logger logger;
    @Autowired private RestTemplate restTemplate;
    @Autowired private ObjectMapper objectMapper;
    private RequestEntity request;

    @Before
    public void setUpRequest() throws URISyntaxException {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("X-API-VERSION", "1");
        request = new RequestEntity(
                headers,
                HttpMethod.GET,
                new URI("http://localhost:" + port + "/api" + "/books")
        );
    }

    @Ignore("To FIX")
    @Test
    public void shouldGetAllBookWhenUsePrePopulatedFakeDb() {
        //TODO Can be simple but need for headers: restTemplate.getForObject(baseUrl + "/books", Book[].class);
        ResponseEntity<Book[]> response = restTemplate.exchange(request, Book[].class);
        logger.debug(">>>>> ");
        for (Book book : response.getBody()) {
            logger.debug(book.toString());
        }

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsOnly(
                new FictionBook("The Count of Monte Cristo", "Alexandre Dumas"),
                new FictionBook("A Game of Thrones", "George R.R. Martin"),
                new FictionBook("George R.R. Martin", "Antoine de Saint-Exupery"),
                new NonFictionBook("Between the world and me", "Ta-Nehisi Coates"),
                new NonFictionBook("1176", "David McCullough"));
    }
}
