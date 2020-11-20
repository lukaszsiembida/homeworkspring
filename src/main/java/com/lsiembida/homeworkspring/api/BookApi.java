package com.lsiembida.homeworkspring.api;


import com.lsiembida.homeworkspring.api.model.BookSearchParams;
import com.lsiembida.homeworkspring.domain.book.Book;
import com.lsiembida.homeworkspring.domain.book.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/book")
public class BookApi {

    private BookService bookService;
    private Validator validator;

    @GetMapping
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/search")
    public List<Book> getByParams(@RequestBody BookSearchParams bookSearchParams) {
        return bookService.searchByParams(bookSearchParams);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getOne(@PathVariable Long bookId) {
        return bookService.findById(bookId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createBook(@RequestBody Book book, BindingResult bindingResult) {
        validator.validate(book, bindingResult);
        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(err -> err.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        bookService.create(book);
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public void updateBook(@RequestBody Book book){
        bookService.update(book);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@RequestParam Long bookId){
        bookService.delete(bookId);
    }
}
