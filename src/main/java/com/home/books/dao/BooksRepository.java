package com.home.books.dao;

import com.home.books.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    Collection<Book> findByAuthor(String author);
    /** TODO Intro to Optional class */
}
