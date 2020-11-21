package com.lsiembida.homeworkspring.external.book;

import com.lsiembida.homeworkspring.api.model.BookSearchParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaBookRepository extends JpaRepository<BookEntity, Long> , CustomDatebaseBookRepository {


    Optional<BookEntity> findByIsbn(String isbn);

    List<BookEntity> findByAuthor(String author);

    List<BookEntity> findByAuthorAndTitle(String author, String title);

    List<BookEntity> findByYearOfPublishment(Integer yearOfPublishment);

    List<BookEntity> findByYearOfPublishmentGreaterThan(Integer year);

    void deleteByTitle(String title);

    Long countByYearOfPublishment(int yearOfPublishment);

    List<BookEntity> findByYearOfPublishmentLessThan(int yearOfPublishment);

    @Query("select book from BookEntity book where book.title = :title")
    List<BookEntity> findBooksByTitle(@Param("title") String title);

    void deleteByIsbnStartingWith(String preIsbn);

    List<BookEntity> findByYearOfPublishmentIsBetween(Integer publicationStart, Integer publicationEnd);


    List<BookEntity> findByRent_RentedToBefore(LocalDate lastEndDate);

    // jpql method
    @Query("select book from BookEntity book inner join book.rent rent " +
            "where rent.rentedFrom < :date and rent.rentedTo > :date")  // jezyk jpql
    List<BookEntity> findRentedBooksAtDay(@Param("date") LocalDate date);

    @Query("select count(book) from BookEntity book inner join book.rent rent " +
            "where rent.rentedFrom > :date and rent.rentedTo < :date")
    Long countNoRentedAtDate(@Param("date") LocalDate date);
    
}
