package com.lsiembida.homeworkspring.api;

import com.lsiembida.homeworkspring.api.model.BookSearchParams;
import com.lsiembida.homeworkspring.config.RestTemplateConfig;
import com.lsiembida.homeworkspring.domain.book.Book;
import com.lsiembida.homeworkspring.external.book.BookEntity;
import com.lsiembida.homeworkspring.external.book.JpaBookRepository;
import com.lsiembida.homeworkspring.external.book.RentEntity;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = RestTemplateConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApiITTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JpaBookRepository bookRepository;

    @BeforeEach
    public void cleanDb() {
        bookRepository.deleteAll();
    }

    @Test
    public void shouldDeleteBookFromDb() {
        //given
        BookEntity bookEntity = new BookEntity(null, "Zemsta", "A. Fredro", 2009,
                "9788361083191", 5, new RentEntity(null, LocalDate.now(), LocalDate.now()));
        BookEntity persistedBook = bookRepository.save(bookEntity);
        //when
        restTemplate.delete(String.format("http://localhost:%d/api/book?bookId=%d", port, persistedBook.getId()));
        //then
        Assertions.assertTrue(bookRepository.count() == 0);
    }

    @Test
    public void shouldNotPassValidationWhenCreatingBook() {
        //given
        Book newBook = new Book(null, null, "A. Fredro", -210,
                "9788383191", 5, LocalDate.now(), LocalDate.now().plusWeeks(5));
        HttpEntity<Book> entity = new HttpEntity<>(newBook);
        //when
        ResponseEntity<List> rsp = restTemplate.exchange(String.format("http://localhost:%d/api/book", port),
                HttpMethod.POST, entity, List.class);
        //then
        Assertions.assertEquals(400, rsp.getStatusCodeValue());

        List errors = rsp.getBody();
        Assertions.assertTrue(errors.contains("Title should be not blank"));
        Assertions.assertTrue(errors.contains("Year of publishment could not be negative"));
        Assertions.assertTrue(errors.contains("ISBN should not be empty and should have 13 signs and starts with the '978'"));
    }

    @Test
    public void shouldCreateBook() {
        //given
        Book newBook = new Book(null, "Zemsta", "A. Fredro", 2009,
                "9788361083191", 5, LocalDate.now(), LocalDate.now().plusWeeks(5));
        HttpEntity<Book> entity = new HttpEntity<>(newBook);
        //when
        ResponseEntity<Void> rsp = restTemplate.exchange(String.format("http://localhost:%d/api/book", port),
                HttpMethod.POST, entity, Void.class);
        //then
        Assertions.assertEquals(201, rsp.getStatusCodeValue());

        List<BookEntity> all = bookRepository.findAll();
        Assertions.assertEquals(1, all.size());

        BookEntity persistedBook = all.get(0);
        Assertions.assertEquals("Zemsta", persistedBook.getTitle());
        Assertions.assertEquals("A. Fredro", persistedBook.getAuthor());
        Assertions.assertEquals("9788361083191", persistedBook.getIsbn());
        Assertions.assertEquals(2009, persistedBook.getYearOfPublishment());
    }

    @Test
    public void shouldGetAllBooksFromDb() {
        //given
        BookEntity bookEntity1 = new BookEntity(null, "Zemsta", "A. Fredro", 2009,
                "9788361083191", 5, new RentEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        BookEntity bookEntity2 = new BookEntity(null, "Potop", "H. Sienkiewicz", 2018,
                "9781985167803", 6, new RentEntity(null, LocalDate.now(), LocalDate.now().plusMonths(2)));
        BookEntity bookEntity3 = new BookEntity(null, "Lalka", "B. Prus", 1990,
                "9788306020779", 3, new RentEntity(null, LocalDate.now(), LocalDate.now().plusMonths(10)));
        bookRepository.saveAll(Lists.list(bookEntity1, bookEntity2, bookEntity3));
        //when
        ResponseEntity<List> rsp = restTemplate.exchange(String.format("http://localhost:%d/api/book", port),
                HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), List.class);
        //then
        Assertions.assertEquals(200, rsp.getStatusCodeValue());
        Assertions.assertEquals(3, rsp.getBody().size());
    }

    @Test
    public void shouldUpdateBook() {
        //given
        BookEntity bookEntity = new BookEntity(null, "Falka", "C. Prus", 1993,
                "9788306020779", 3, new RentEntity(null, LocalDate.now(), LocalDate.now().plusMonths(10)));
        BookEntity persistedBook = bookRepository.save(bookEntity);

        Book updateRequest = new Book(null, "Lalka", "B. Prus", 1990,
                "9788306020779", 3,LocalDate.now(), LocalDate.now().plusMonths(7));

        HttpEntity<Book> entity = new HttpEntity<>(updateRequest);
        //when
        ResponseEntity<Void> rsp = restTemplate.exchange(String.format("http://localhost:%d/api/book", port),
                HttpMethod.PUT, entity, Void.class);
        //then
        Assertions.assertEquals(200, rsp.getStatusCodeValue());

        BookEntity updatedBook = bookRepository.findById(persistedBook.getId()).get();
        Assertions.assertEquals("Lalka", updatedBook.getTitle());
        Assertions.assertEquals("B. Prus", updatedBook.getAuthor());
        Assertions.assertEquals(1990, updatedBook.getYearOfPublishment());
    }

    @Test
    public void shouldGetBooksByTitle() {
        //given
        BookEntity bookEntity1 = new BookEntity(null, "Zemsta", "A. Fredro", 2009,
                "9788361083191", 5, new RentEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        BookEntity bookEntity2 = new BookEntity(null, "Potop", "H. Sienkiewicz", 2018,
                "9781985167803", 6, new RentEntity(null, LocalDate.now(), LocalDate.now().plusMonths(2)));
        BookEntity bookEntity3 = new BookEntity(null, "Lalka", "B. Prus", 1990,
                "9788306020779", 3, new RentEntity(null, LocalDate.now(), LocalDate.now().plusMonths(10)));
        bookRepository.saveAll(Lists.list(bookEntity1, bookEntity2, bookEntity3));

        BookSearchParams searchParams = new BookSearchParams();
        searchParams.setTitle("Potop");

        HttpEntity<BookSearchParams> entity = new HttpEntity<>(searchParams);
        //when
        ResponseEntity<List<Book>> rsp = restTemplate.exchange(String.format("http://localhost:%d/api/book/search", port),
                HttpMethod.POST, entity, new ParameterizedTypeReference<List<Book>>() {});
        //then
        Assertions.assertEquals(200, rsp.getStatusCodeValue());
        Assertions.assertEquals(2, rsp.getBody().size());
        Assertions.assertTrue(rsp.getBody().stream().allMatch(book -> book.getTitle().equals("Potop")));
    }
}
