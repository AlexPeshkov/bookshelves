package com.home.books;

import com.home.books.domain.NonFictionBook;
import com.home.books.dao.BooksRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //TODO Semantics of MOCK: No servlet container started
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
public class BookControllerIT {
    @Autowired private MockMvc mockMvc;
    @Autowired private Logger logger;
    @MockBean private BooksRepository books; //TODO MockBean semantics

    @Ignore("TO FIX")
    @Test
    public void shouldGetNoBooksWhenBooksRepoIsEmpty() throws Exception {
        when(books.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/books").header("X-API-VERSION", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value("0"));
    }

    @Test
    public void shouldGetTheOneBookWhenRepoIsPrePopulated() throws Exception {
        NonFictionBook bookstub = new NonFictionBook("Test Book", "Test Author");
        when(books.findAll()).thenReturn(asList(bookstub));

        mockMvc.perform(get("/api/books").header("X-API-VERSION", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value("1"));
                    //.andExpect(jsonPath("$[0].amount").value("100.0"))
                    //.andExpect(jsonPath("$[0].email").value("sa@sa.sa"));
    }
}
