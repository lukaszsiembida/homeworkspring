package com.lsiembida.homeworkspring.web;

import com.lsiembida.homeworkspring.api.model.BookSearchParams;
import com.lsiembida.homeworkspring.domain.book.Book;
import com.lsiembida.homeworkspring.domain.book.BookService;
import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
@RequestMapping("mvc/book")
public class BookController {

    private BookService bookService;  // wstrzyknięcie za pomocą konstruktora

    @RequestMapping
    @PreAuthorize("isAuthenticated()")
    ModelAndView displayBookPage(){
        ModelAndView mav = new ModelAndView("books.html");
        mav.addObject("books", bookService.getAll());
        mav.addObject("todayDate", LocalDate.now());
        mav.addObject("params", new BookSearchParams());

        return mav;
    }

    @PostMapping("/search")
    @PreAuthorize("isAuthenticated()")
    ModelAndView handleBookFiltering(@ModelAttribute("params") BookSearchParams params){
        ModelAndView mav = new ModelAndView("books.html");
        mav.addObject("books", bookService.searchByParams(params));
        mav.addObject("todayDate", LocalDate.now());
        mav.addObject("params", params);
        return mav;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    ModelAndView displayAddBookPage(){
        ModelAndView mav = new ModelAndView("addBook.html");
        mav.addObject("book", new Book()); /*wyświetlenie formularza car z th:object*/
        return mav;
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ModelAndView displayAddBookPage(@PathVariable Long id){
        Optional<Book> book = bookService.findById(id);
        ModelAndView mav = new ModelAndView();
        if(book.isPresent()){
            mav.addObject("book", book.get());  /*wyświetlenie formularza book z th:object*/
            mav.setViewName("addBook.html");
        } else {
            mav.addObject("message", String.format("Książka z id %d nie istnieje", id));
            mav.setViewName("error.html");
        }
        return mav;
    }

    @PostMapping("/addOrEdit")
    @PreAuthorize("hasRole('ADMIN')")
    String handleAddBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
         List<String> globalErrors = bindingResult.getGlobalErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
         model.addAttribute("globalErrors", globalErrors);

            return "addBook.html";
        }

        if(book.getId() != null){
            bookService.update(book);
        } else {
            bookService.create(book);
        }
        return "redirect:/mvc/book"; /*przekierowanie stringa za pomocą redirect*/
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    String handledeleteBook(@PathVariable Long id){
        bookService.delete(id);
        return "redirect:/mvc/book";
    }
}
