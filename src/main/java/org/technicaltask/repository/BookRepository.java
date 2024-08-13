package org.technicaltask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.technicaltask.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBookByTitleAndAuthor(String title, String author);

    Boolean existsByTitleAndAuthor(String title, String author);
}
