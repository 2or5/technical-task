package org.technicaltask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.technicaltask.dto.BookDto;
import org.technicaltask.entity.Book;
import org.technicaltask.exception.IdNotFoundException;
import org.technicaltask.repository.BookRepository;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("The book does not exist by this id: " + id));

        return BookDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .amount(book.getAmount())
                .build();
    }

    public void saveBook(BookDto bookDto) {
        Book book = Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .build();

        Boolean isBookExist = bookRepository.existsByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor());
        if (isBookExist) {
            saveExistedBook(bookDto);
        } else {
            bookRepository.save(book);
        }
    }

    private void saveExistedBook(BookDto bookDto) {
        Book existedBook = bookRepository.findBookByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor());
        existedBook.setAmount(existedBook.getAmount() + 1);
        bookRepository.save(existedBook);
    }

    public void updateBook(BookDto bookDto, Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("The book does not exist by this id: " + id));
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setAuthor(bookDto.getAuthor());

        bookRepository.save(book);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
