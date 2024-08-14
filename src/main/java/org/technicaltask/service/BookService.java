package org.technicaltask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.technicaltask.dto.BookDtoResponseWithCounter;
import org.technicaltask.dto.EditBookDto;
import org.technicaltask.dto.BookDtoResponse;
import org.technicaltask.dto.SaveBookDto;
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

    public BookDtoResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("The book does not exist by this id: " + id));

        return BookDtoResponse.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .amount(book.getAmount())
                .build();
    }

    public void saveBook(SaveBookDto saveBookDto) {
        Book book = Book.builder()
                .title(saveBookDto.getTitle())
                .author(saveBookDto.getAuthor())
                .build();

        Boolean isBookExist = bookRepository.existsByTitleAndAuthor(saveBookDto.getTitle(), saveBookDto.getAuthor());
        if (isBookExist) {
            saveExistedBook(saveBookDto);
        } else {
            book.setAmount(1);
            bookRepository.save(book);
        }
    }

    private void saveExistedBook(SaveBookDto saveBookDto) {
        Book existedBook = bookRepository.findBookByTitleAndAuthor(saveBookDto.getTitle(), saveBookDto.getAuthor());
        existedBook.setAmount(existedBook.getAmount() + 1);
        bookRepository.save(existedBook);
    }

    public void updateBook(EditBookDto bookDto, Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("The book does not exist by this id: " + id));
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setAmount(bookDto.getAmount());

        bookRepository.save(book);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getBorrowedBooksByMemberName(String memberName) {
        return bookRepository.getBorrowedBooksByMemberName(memberName);
    }

    public List<String> getBorrowedBookNames() {
        return bookRepository.getBorrowedBookNames();
    }

    public List<BookDtoResponseWithCounter> getBorrowedBookNamesWithCounter() {
        return bookRepository.getBorrowedBookNamesWithCounter();
    }
}
