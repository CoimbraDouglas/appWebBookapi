package com.example.bookapi.service;

import com.example.bookapi.model.Book;
import com.example.bookapi.repository.BookRepository;
import com.example.bookapi.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository repo;

    @InjectMocks
    private BookServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Book sample() {
        Book b = new Book();
        b.setId(1L);
        b.setTitle("Título");
        b.setAuthor("Autor");
        b.setPublishedDate(LocalDate.now());
        b.setPrice(new BigDecimal("29.90"));
        return b;
    }

    @Test
    void create_shouldSaveBook() {
        Book b = sample();
        when(repo.save(any(Book.class))).thenReturn(b);

        Book result = service.create(b);

        assertNotNull(result);
        assertEquals("Título", result.getTitle());
        verify(repo, times(1)).save(b);
    }

    @Test
    void findAll_shouldReturnList() {
        Book b = sample();
        when(repo.findAll()).thenReturn(List.of(b));

        List<Book> list = service.findAll();
        assertFalse(list.isEmpty());
        verify(repo).findAll();
    }

    @Test
    void findById_found() {
        Book b = sample();
        when(repo.findById(1L)).thenReturn(Optional.of(b));

        Optional<Book> opt = service.findById(1L);
        assertTrue(opt.isPresent());
        assertEquals("Título", opt.get().getTitle());
    }

    @Test
    void update_existing() {
        Book existing = sample();
        Book updated = sample();
        updated.setTitle("Novo Título");
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book result = service.update(1L, updated);
        assertEquals("Novo Título", result.getTitle());
        verify(repo).findById(1L);
        verify(repo).save(existing);
    }

    @Test
    void delete_shouldCallRepository() {
        doNothing().when(repo).deleteById(1L);
        service.delete(1L);
        verify(repo).deleteById(1L);
    }
}
