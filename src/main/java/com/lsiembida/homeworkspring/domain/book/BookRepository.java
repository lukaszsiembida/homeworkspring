package com.lsiembida.homeworkspring.domain.book;

import com.lsiembida.homeworkspring.api.model.BookSearchParams;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Optional<Book> findOne(Long id);
    boolean existByIsbn(String isbn);
    List<Book> findAll();
    void create(Book book);
    void update(Book book);
    void delete(Long id);
    List<Book> findByParams(BookSearchParams bookSearchParams);
}
