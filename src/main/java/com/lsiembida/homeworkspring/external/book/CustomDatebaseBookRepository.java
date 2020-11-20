package com.lsiembida.homeworkspring.external.book;

import com.lsiembida.homeworkspring.api.model.BookSearchParams;

import java.util.List;

public interface CustomDatebaseBookRepository {

    List<BookEntity> findBasedOnSearchParams(BookSearchParams bookSearchParams);


}
