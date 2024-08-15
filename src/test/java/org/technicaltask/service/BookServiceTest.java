package org.technicaltask.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.technicaltask.dto.BookDtoResponse;
import org.technicaltask.dto.BookDtoResponseWithCounter;
import org.technicaltask.dto.EditBookDto;
import org.technicaltask.dto.SaveBookDto;
import org.technicaltask.entity.Book;
import org.technicaltask.entity.Member;
import org.technicaltask.exception.IdNotFoundException;
import org.technicaltask.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void getBooksTest() {
        List<Book> books = List.of(
                new Book(1L, "Test", "Test Test", 2, new ArrayList<>()),
                new Book(2L, "Test", "Test Test", 3, new ArrayList<>())
        );

        when(bookRepository.findAll()).thenReturn(books);
        assertEquals(books, bookService.getBooks());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void getBookByIdTest() {
        var book = new Book(1L, "Test", "Test Test", 2, new ArrayList<>());
        var bookDto = BookDtoResponse.builder()
                .title("Test")
                .author("Test Test")
                .amount(2)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        assertEquals(bookDto, bookService.getBookById(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void getBookByIdWithInvalidIdTest() {
        assertThrows(IdNotFoundException.class,
                () -> bookService.getBookById(100L));
        verify(bookRepository, times(1)).findById(100L);
    }

    @Test
    public void saveBook() {
        var book = new Book(null, "Test", "Test Test", 1, null);
        var bookDto = SaveBookDto.builder()
                .title("Test")
                .author("Test Test")
                .build();

        when(bookRepository.existsByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor()))
                .thenReturn(false);
        bookService.saveBook(bookDto);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void saveBookWithExistedBookTest() {
        var book = new Book(null, "Test", "Test Test", 1, null);
        var bookDto = SaveBookDto.builder()
                .title("Test")
                .author("Test Test")
                .build();

        when(bookRepository.existsByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor()))
                .thenReturn(true);
        when(bookRepository.findBookByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor()))
                .thenReturn(book);
        bookService.saveBook(bookDto);
        verify(bookRepository, times(1)).existsByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor());
        verify(bookRepository, times(1)).findBookByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void updateBookTest() {
        var book = new Book(null, "Test", "Test Test", 1, null);
        var bookDto = EditBookDto.builder()
                .title("Test")
                .author("Test Test")
                .amount(2)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        bookService.updateBook(bookDto, 1L);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void deleteBookByIdTest() {
        var book = new Book(1L, "Test", "Test", 2, new ArrayList<>());
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        bookService.deleteBookById(1L);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
    @Test
    public void deleteBookByIdWithMemberTest() {
        var member = new Member();
        var members = new ArrayList<Member>();
        members.add(member);
        var book = new Book(1L, "Test", "Test", 2, members);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        assertThrows(IllegalStateException.class, () -> bookService.deleteBookById(1L));
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).deleteById(1L);
    }

    @Test
    public void getBorrowedBooksByMemberNameTest() {
        String memberName = "Test";
        List<Book> books = List.of(
                new Book(1L, "Test", "Test Test", 2, new ArrayList<>()),
                new Book(2L, "Test", "Test Test", 3, new ArrayList<>())
        );

        when(bookRepository.getBorrowedBooksByMemberName(memberName)).thenReturn(books);
        assertEquals(books, bookService.getBorrowedBooksByMemberName(memberName));
        verify(bookRepository, times(1)).getBorrowedBooksByMemberName(memberName);
    }

    @Test
    public void getBorrowedBookNamesTest() {
        List<String> strings = List.of("Test", "Test");

        when(bookRepository.getBorrowedBookNames()).thenReturn(strings);
        assertEquals(strings, bookService.getBorrowedBookNames());
        verify(bookRepository, times(1)).getBorrowedBookNames();
    }

    @Test
    public void getBorrowedBookNamesWithCounterTest() {
        List<BookDtoResponseWithCounter> listDto = List.of(
                new BookDtoResponseWithCounter("Test", 1L),
                new BookDtoResponseWithCounter("Test2", 2L));

        when(bookRepository.getBorrowedBookNamesWithCounter()).thenReturn(listDto);
        assertEquals(listDto, bookService.getBorrowedBookNamesWithCounter());
        verify(bookRepository, times(1)).getBorrowedBookNamesWithCounter();

    }
}
