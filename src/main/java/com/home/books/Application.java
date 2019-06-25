package com.home.books;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * How to Run:
 * > java -jar target/bookshelves-1.0-SNAPSHOT.jar -Dfile.encoding=Cp866
 * or
 * > mvn spring-boot:run
 *
 * > Swagger : http://localhost:8080/swagger-ui.html
 */
@SpringBootApplication
@ImportResource("classpath:spring-config.xml")
public class Application {
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3_000);
        factory.setReadTimeout(3_000);
        return new RestTemplate(factory);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public ObjectMapper getObjectMapper() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return objectMapper;
    }
}