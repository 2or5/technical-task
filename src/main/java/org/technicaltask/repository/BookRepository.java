package org.technicaltask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.technicaltask.dto.BookDtoResponse;
import org.technicaltask.entity.Book;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBookByTitleAndAuthor(String title, String author);

    Boolean existsByTitleAndAuthor(String title, String author);

    @Query("SELECT b FROM Book b JOIN b.members m WHERE m.name LIKE :memberName")
    List<Book> getBorrowedBooksByMemberName(String memberName);

    @Query("SELECT DISTINCT b.title FROM Book b JOIN b.members")
    List<String> getBorrowedBookNames();

    @Query("SELECT new org.technicaltask.dto.BookDtoResponse(b.title, COUNT(m)) " +
            "FROM Book b JOIN b.members m " +
            "GROUP BY b.title")
    List<BookDtoResponse> getBorrowedBookNamesWithCounter();
}
