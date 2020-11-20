package com.lsiembida.homeworkspring.domain.book;

import com.lsiembida.homeworkspring.api.validator.Isbn;
import com.lsiembida.homeworkspring.api.validator.RentPeriod;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@RentPeriod
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private Long id;
    @NotBlank(message = "Title should be not blank")
    private String title;
    private String author;
    @NotNull
    @Positive(message = "Year of publishment could not be negative")
    private Integer yearOfPublishment;
    @Isbn
    private String isbn;
    private Integer numberOfCopies;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rentedFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rentedTo;

}
