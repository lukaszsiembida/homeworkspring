package com.lsiembida.homeworkspring.external.book;

import com.lsiembida.homeworkspring.domain.book.BookRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;

    private Integer yearOfPublishment;

    @Column(nullable = false, unique = true, length = 13)
    private String isbn;

    @Column(nullable = false)
    private Integer numberOfCopies;

    @OneToOne(cascade = CascadeType.ALL)
    private RentEntity rent;

}
