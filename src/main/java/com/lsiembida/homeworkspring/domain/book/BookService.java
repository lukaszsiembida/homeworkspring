package com.lsiembida.homeworkspring.domain.book;

import com.lsiembida.homeworkspring.api.model.BookSearchParams;
import com.lsiembida.homeworkspring.exception.AlreadyExistException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

  private BookRepository bookRepository;

  public void create(Book book){
        if(bookRepository.existByIsbn(book.getIsbn())){
        throw new AlreadyExistException(String.format("Book with ISBN %s already exists.", book.getIsbn()));
        }
        bookRepository.create(book);
  }

  public void update(Book book){
      bookRepository.update(book);
  }

    public Optional<Book> findById(Long id){
    return bookRepository.findOne(id);
  }

  public List<Book> getAll(){
      return bookRepository.findAll();
  }

  public void delete(Long id){
      bookRepository.delete(id);
  }

    public List<Book> searchByParams (BookSearchParams bookSearchParams){
      return bookRepository.findByParams(bookSearchParams);
    }

}
