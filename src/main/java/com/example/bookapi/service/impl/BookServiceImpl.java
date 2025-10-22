package com.example.bookapi.service.impl;

import com.example.bookapi.model.Book;
import com.example.bookapi.repository.BookRepository;
import com.example.bookapi.service.BookService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repo;

    public BookServiceImpl(BookRepository repo) {
        this.repo = repo;
    }

    @Override
    public Book create(Book book) {
        return repo.save(book);
    }

    @Override
    public List<Book> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Book update(Long id, Book book) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(book.getTitle());
            existing.setAuthor(book.getAuthor());
            existing.setPublishedDate(book.getPublishedDate());
            existing.setPrice(book.getPrice());
            return repo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
