package com.home.books.service;

import com.home.books.dao.BooksRepository;
import com.home.books.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
    private BooksRepository books;

    /** TODO Field VS constructor VS setter injection*/
    /** TODO Identifying issues: semantics for type/id lookup ans @Qualifier("id")/@Primary */
    @Autowired
    public TransferService(BooksRepository books) {
        this.books = books;
    }

    /** TODO Semantics for:
     *  - @Transactional,
     *  - @PreAuthorize + @Secured/@RolesAllowed,
     *  - @Retryable/@Recover,
     *  - @Async,
     *  - @Cacheable + @CacheEvict/@CachePut
     */
    @Transactional
    public void transfer(long fromId, long toId, double amount) {
        Book from = books.findById(fromId).orElseThrow(() -> new IllegalStateException("Book not found"));
        Book to = books.findById(toId).orElseThrow(() -> new IllegalStateException("Book not found"));

        //TO DO Implement book moving from shelve
    }
}
