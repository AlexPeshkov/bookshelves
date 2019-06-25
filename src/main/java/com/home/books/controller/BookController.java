package com.home.books.controller;

import com.home.books.dao.BooksRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.books.domain.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping(path = "/api", headers = "X-API-VERSION")
@Validated
@Api(value="Books")
public class BookController {
    @Resource private BooksRepository books;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private Logger logger;

    @GetMapping(path = "/books", headers = "X-API-VERSION=1")
    @ApiOperation(value = "View a list of all books", response = Collection.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all books"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    public Collection<Book> getAllBooks() {
        return books.findAll();
    }

    /**
     * @param id is @Valid out-of-box by using @Validated annotation for Controller class
     */
    @GetMapping(path = "/books/{id}", headers = "X-API-VERSION=1")
    public ResponseEntity<Book> getBookInfo(@PathVariable @PositiveOrZero(message = "No negative id!") long id) {
        return books.findById(id)
                .map(book -> new ResponseEntity<>(book, HttpStatus.FOUND))
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Book is not found, id: " + id
                ));
    }

    @DeleteMapping(path = "/books/{id}", headers = "X-API-VERSION=1")
    public ResponseEntity<?> deleteBookRecord(@PathVariable @PositiveOrZero(message = "No negative id!") long id) {
        try {
            books.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Book is not found,  id: " + id,
                    e
            );
        }
    }

    @PostMapping(path = "/books", headers = "X-API-VERSION=1")
    public Book addBook(@RequestBody @Valid Book book) {
        return books.save(book);
    }
}
