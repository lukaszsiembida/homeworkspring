package com.lsiembida.homeworkspring.web;

import com.lsiembida.homeworkspring.config.CompanyInfo;
import com.lsiembida.homeworkspring.domain.book.Book;
import com.lsiembida.homeworkspring.domain.book.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class MainPageContoller {

    private BookService bookService; // wstrzykiwanie
    private CompanyInfo companyInfo; // wstrzykiwanie

    @GetMapping("/")
    ModelAndView displayMainPage() {
        List<String> bookList = bookService.getAll().stream().map(Book::getIsbn)
                .distinct().collect(Collectors.toList());

        ModelAndView mav = new ModelAndView();
        mav.addObject("date", LocalDate.now().toString());
        mav.addObject("bookList", bookList);
        mav.addObject("info", companyInfo);

        mav.setViewName("main.html");
        return mav;
    }
}
