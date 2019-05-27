package com.home.books;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //TODO Semantics of MOCK: No servlet container started
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
public class BookControllerLightST {
    @Autowired private MockMvc mockMvc;
    @Autowired Logger logger;

    @Ignore("To FIX")
    @Test
    public void shouldGetPrePopulatedBooksFromFakeDb() throws Exception {
        logger.info("Got test books: " +
            mockMvc.perform(get("/api/books").header("X-API-VERSION", "1"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$.length()").value("4"))
                    .andReturn()
                        .getResponse().getContentAsString()
        );
    }

    @Ignore("To FIX")
    //TODO Extend for all json path
    @Test
    public void shouldGetFirstPrePopulatedBookFromFakeDb() throws Exception {
        mockMvc.perform(get("/api/books/1").header("X-API-VERSION", "1"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.type").value("F"));
    }
}
