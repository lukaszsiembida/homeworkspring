package com.lsiembida.homeworkspring.domain.book;

import com.lsiembida.homeworkspring.api.model.BookSearchParams;
import com.lsiembida.homeworkspring.exception.AlreadyExistException;
import com.lsiembida.homeworkspring.external.book.BookEntity;
import com.lsiembida.homeworkspring.external.book.JpaBookRepository;
import com.lsiembida.homeworkspring.external.book.RentEntity;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class BookServiceITTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private JpaBookRepository jpaBookRepository;

    @BeforeEach
    public void cleanDb() {
        jpaBookRepository.deleteAll();
    }

    @Test
    public void shouldReturnEmptyListWhenNoBooksExistInDb() {
        //when
        List<Book> books = bookService.searchByParams(new BookSearchParams());
        //then
        Assertions.assertTrue(books.isEmpty());
    }

    @Test
    public void shouldReturnByGivenTitle() {
        //given
        BookEntity bookEntity1 = new BookEntity(null, "Zemsta", "A. Fredro", 2009,
                "9788361083191", 5, new RentEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        BookEntity bookEntity2 = new BookEntity(null, "Potop", "H. Sienkiewicz", 2018,
                "9781985167803", 6, new RentEntity(null, LocalDate.now(), LocalDate.now().plusMonths(2)));
        BookEntity bookEntity3 = new BookEntity(null, "Lalka", "B. Prus", 1990,
                "9788306020779", 3, new RentEntity(null, LocalDate.now(), LocalDate.now().plusMonths(10)));
        jpaBookRepository.saveAll(Lists.list(bookEntity1, bookEntity2, bookEntity3));
        BookSearchParams searchParams = new BookSearchParams();
        searchParams.setTitle("Zemsta");
        //when
        List<Book> bookList = bookService.searchByParams(searchParams);
        //then
        Assertions.assertEquals(2, bookList.size());
        Assertions.assertTrue(bookList.stream().allMatch(book -> book.getTitle().equals("Zemsta")));
    }

    @Test
    public void shouldReturnOnlyNeverThanGivenPublishmentYear() {
        //given
        BookEntity bookEntity1 = new BookEntity(null, "Zemsta", "A. Fredro", 2009,
                "9788361083191", 5, new RentEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        BookEntity bookEntity2 = new BookEntity(null, "Potop", "H. Sienkiewicz", 2018,
                "9781985167803", 6, new RentEntity(null, LocalDate.now(), LocalDate.now().plusMonths(2)));
        BookEntity bookEntity3 = new BookEntity(null, "Lalka", "B. Prus", 1990,
                "9788306020779", 3, new RentEntity(null, LocalDate.now(), LocalDate.now().plusMonths(10)));
        jpaBookRepository.saveAll(Lists.list(bookEntity1, bookEntity2, bookEntity3));
        BookSearchParams searchParams = new BookSearchParams();
        searchParams.setYearOfPublishment(2009);
        //when
        List<Book> books = bookService.searchByParams(searchParams);
        //then
        Assertions.assertEquals(1, books.size());
        Assertions.assertTrue(books.stream().allMatch(book -> book.getYearOfPublishment() > 2012));
    }

    @Test
    public void shouldPersistBookInDb() {
        //given
        LocalDate rentBegin = LocalDate.now();
        LocalDate rentEnd = rentBegin.minusMonths(6);
       Book book = new Book(null, "Chłopi", "W. Reymont", 1975, "9780359989713",
                10, rentBegin, rentEnd);
        //when
        bookService.create(book);
        //then
        List<BookEntity> all = jpaBookRepository.findAll();
        Assertions.assertEquals(1, all.size());

        BookEntity addedBook = all.get(0);
        Assertions.assertEquals("Skoda", addedBook.getTitle());
        Assertions.assertEquals("Fabia", addedBook.getAuthor());
        Assertions.assertEquals(2015, addedBook.getYearOfPublishment());
        Assertions.assertEquals("ABCD", addedBook.getIsbn());
        Assertions.assertEquals(rentBegin, addedBook.getRent().getRentedFrom());
        Assertions.assertEquals(rentEnd, addedBook.getRent().getRentedTo());
    }

    @Test
    public void shouldNotPersistBookWhenIsbnAlreadyExists() {
        //given
        LocalDate rentBegin = LocalDate.now();
        LocalDate rentEnd = rentBegin.minusMonths(6);
        Book book = new Book(null, "Chłopi", "W. Reymont", 1975, "9780359989713",
                10, rentBegin, rentEnd);

        BookEntity bookEntity = new BookEntity(null, "Kaczka", "J. Tuwim", 2002,
                "9780359989713",7, new RentEntity(null, rentBegin, rentEnd));
        jpaBookRepository.save(bookEntity);
        //when
        AlreadyExistException ex = Assertions.assertThrows(AlreadyExistException.class,
                () -> bookService.create(book));
        //then
        Assertions.assertEquals("Book with ISBN 9780359989713 already exists.", ex.getMessage());
    }
}
