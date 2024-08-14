package org.technicaltask.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.technicaltask.dto.BookDto;
import org.technicaltask.dto.BookDtoResponse;
import org.technicaltask.entity.Book;
import org.technicaltask.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public void saveBook(@Valid @RequestBody BookDto bookDto) {
        bookService.saveBook(bookDto);
    }

    @PutMapping("/{id}")
    public void updateBook(@Valid @RequestBody BookDto bookDto, @PathVariable Long id) {
        bookService.updateBook(bookDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

    @GetMapping("/getBorrowedBooksByMemberName")
    public List<Book> getBorrowedBooksByMemberName(@RequestParam String memberName) {
        return bookService.getBorrowedBooksByMemberName(memberName);
    }

    @GetMapping("/getBorrowedBookNames")
    public List<String> getBorrowedBookNames() {
        return bookService.getBorrowedBookNames();
    }

    @GetMapping("/getBorrowedBookNamesWithCounter")
    public List<BookDtoResponse> getBorrowedBookNamesWithCounter() {
        return bookService.getBorrowedBookNamesWithCounter();
    }
}
