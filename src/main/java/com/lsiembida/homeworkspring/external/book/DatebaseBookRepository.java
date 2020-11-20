package com.lsiembida.homeworkspring.external.book;

import com.lsiembida.homeworkspring.api.model.BookSearchParams;
import com.lsiembida.homeworkspring.domain.book.Book;
import com.lsiembida.homeworkspring.domain.book.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class DatebaseBookRepository implements BookRepository {

    JpaBookRepository jpaBookRepository;

    @Override
    public Optional<Book> findOne(Long id) {
        return jpaBookRepository.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existByIsbn(String isbn) {
        return jpaBookRepository.findByIsbn(isbn).isPresent();
    }

    @Override
    public List<Book> findAll() {
        return jpaBookRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void create(Book book) {
        jpaBookRepository.save(toEntity(book));
    }

    @Override
    public void update(Book book) {
        if (!jpaBookRepository.existsById(book.getId())) {
            throw new IllegalStateException("Updated object not exist");
        }
        jpaBookRepository.save(toEntity(book));
    }

    @Override
    public void delete(Long id) {
    jpaBookRepository.deleteById(id);
    }

    @Override
    public List<Book> findByParams(BookSearchParams bookSearchParams) {
        return jpaBookRepository.findBasedOnSearchParams(bookSearchParams).stream()
                .map(this::toDomain).collect(Collectors.toList());
    }

    private Book toDomain(BookEntity entity) {
        return Book.builder().id(entity.getId()).title(entity.getTitle())
                .author(entity.getAuthor()).isbn(entity.getIsbn())
                .yearOfPublishment(entity.getYearOfPublishment())
                .rentedFrom(entity.getRent().getRentedFrom())
                .rentedTo(entity.getRent().getRentedTo())
                .numberOfCopies(entity.getNumberOfCopies())
                .build();
    }

    private BookEntity toEntity(Book book) {
        return BookEntity.builder().id(book.getId()).title(book.getTitle())
                .author(book.getAuthor()).isbn(book.getIsbn())
                .yearOfPublishment(book.getYearOfPublishment())
                .numberOfCopies(book.getNumberOfCopies())
                .rent(RentEntity.builder()
                        .rentedFrom(book.getRentedFrom())
                        .rentedTo(book.getRentedTo()).build())
                .build();
    }
}
