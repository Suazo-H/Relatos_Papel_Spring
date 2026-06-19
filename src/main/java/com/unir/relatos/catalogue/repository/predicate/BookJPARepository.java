package com.unir.relatos.catalogue.repository.predicate;

import com.unir.relatos.catalogue.repository.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookJPARepository extends
        JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book>,
        PagingAndSortingRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.stock > 0")
    List<Book> findAvailableBooks();

    List<Book> findByGenreIgnoreCase(String genre);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByIsbnContainingIgnoreCase(String isbn);

    List<Book> findByStockGreaterThan(Integer stock);

    List<Book> findByVisibleTrue();
}
