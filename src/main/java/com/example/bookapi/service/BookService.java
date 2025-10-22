package com.example.bookapi.service;

import com.example.bookapi.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Book create(Book book);
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book update(Long id, Book book);
    void delete(Long id);
}
