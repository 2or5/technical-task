package org.technicaltask.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import org.technicaltask.dto.BookDtoResponseWithCounter;
import org.technicaltask.dto.EditBookDto;
import org.technicaltask.dto.BookDtoResponse;
import org.technicaltask.dto.SaveBookDto;
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

    @Operation(summary = "Get list of books",
            description = "This endpoint allows you to get all books.")
    @GetMapping
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @Operation(summary = "Get book by id",
            description = "This endpoint allows you to get book by id.")
    @GetMapping("/{id}")
    public BookDtoResponse getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @Operation(summary = "Save book",
            description = "This endpoint allows you to save book.")
    @PostMapping
    public void saveBook(@Valid @RequestBody SaveBookDto saveBookDto) {
        bookService.saveBook(saveBookDto);
    }

    @Operation(summary = "Edit book",
            description = "This endpoint allows you to edit book by id.")
    @PutMapping("/{id}")
    public void updateBook(@Valid @RequestBody EditBookDto bookDto, @PathVariable Long id) {
        bookService.updateBook(bookDto, id);
    }

    @Operation(summary = "Delete book by id",
            description = "This endpoint allows you to delete book by id.")
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

    @Operation(summary = "Get borrowed books by member name",
            description = "This endpoint allows you to get borrowed books by member name.")
    @GetMapping("/getBorrowedBooksByMemberName")
    public List<Book> getBorrowedBooksByMemberName(@RequestParam String memberName) {
        return bookService.getBorrowedBooksByMemberName(memberName);
    }

    @Operation(summary = "Get list of string title of borrowed books",
            description = "This endpoint allows you to get titles of borrowed books.")
    @GetMapping("/getBorrowedBookNames")
    public List<String> getBorrowedBookNames() {
        return bookService.getBorrowedBookNames();
    }

    @Operation(summary = "Get list of books titles of borrowed books with counter",
            description = "This endpoint allows you to get titles of borrowed books and amount how much copy of this book was borrowed.")
    @GetMapping("/getBorrowedBookNamesWithCounter")
    public List<BookDtoResponseWithCounter> getBorrowedBookNamesWithCounter() {
        return bookService.getBorrowedBookNamesWithCounter();
    }
}
