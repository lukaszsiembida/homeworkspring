package com.lsiembida.homeworkspring.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookSearchParams {

    private String title;
    private String author;
    private Integer yearOfPublishment;
    private String isbn;
    private Integer numberOfCopies;
    private Integer rentedTo;
    private Integer rentedFrom;


}
