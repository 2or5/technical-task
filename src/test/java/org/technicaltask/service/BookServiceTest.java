//package org.technicaltask.service;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.technicaltask.dto.BookDto;
//import org.technicaltask.entity.Book;
//import org.technicaltask.exception.IdNotFoundException;
//import org.technicaltask.repository.BookRepository;
//import java.util.List;
//import java.util.Optional;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//@RunWith(SpringRunner.class)
//public class BookServiceTest {
//
//    @Mock
//    private BookRepository bookRepository;
//
//    @InjectMocks
//    private BookService bookService;
//
//    @Test
//    public void getBooksTest() {
//        List<Book> books = List.of(
//                new Book(1L, "Test", "Test Test", 2),
//                new Book(2L, "Test", "Test Test", 3));
//
//        when(bookRepository.findAll()).thenReturn(books);
//        assertEquals(books, bookService.getBooks());
//        verify(bookRepository, times(1)).findAll();
//    }
//
//    @Test
//    public void getBookByIdTest() {
//        var book = new Book(1L, "Test", "Test Test", 2);
//        var bookDto = BookDto.builder()
//                .title("Test")
//                .author("Test Test")
//                .amount(2)
//                .build();
//
//        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
//        assertEquals(bookDto, bookService.getBookById(1L));
//        verify(bookRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    public void getBookByIdWithInvalidIdTest() {
//        assertThrows(IdNotFoundException.class,
//                () -> bookService.getBookById(100L));
//        verify(bookRepository, times(1)).findById(100L);
//    }
//
//    @Test
//    public void saveBook() {
//        var book = new Book(null, "Test", "Test Test", null);
//        var bookDto = BookDto.builder()
//                .title("Test")
//                .author("Test Test")
//                .build();
//
//        when(bookRepository.existsByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor()))
//                .thenReturn(false);
//        bookService.saveBook(bookDto);
//        verify(bookRepository, times(1)).save(book);
//    }
//
//    @Test
//    public void saveBookWithExistedBookTest() {
//        var book = new Book(1L, "Test", "Test Test", 2);
//        var bookDto = BookDto.builder()
//                .title("Test")
//                .author("Test Test")
//                .amount(2)
//                .build();
//
//        when(bookRepository.existsByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor()))
//                .thenReturn(true);
//        when(bookRepository.findBookByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor()))
//                .thenReturn(book);
//        bookService.saveBook(bookDto);
//        verify(bookRepository, times(1)).existsByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor());
//        verify(bookRepository, times(1)).findBookByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor());
//        verify(bookRepository, times(1)).save(book);
//    }
//
//    @Test
//    public void updateMemberTest() {
//        var book = new Book(1L, "Test", "Test Test", 2);
//        var bookDto = BookDto.builder()
//                .title("Test")
//                .author("Test Test")
//                .amount(2)
//                .build();
//
//        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
//        bookService.updateBook(bookDto, 1L);
//        verify(bookRepository, times(1)).save(book);
//    }
//
//    @Test
//    public void deleteBookByIdTest() {
//        bookService.deleteBookById(1L);
//        verify(bookRepository, times(1)).deleteById(1L);
//    }
//}
