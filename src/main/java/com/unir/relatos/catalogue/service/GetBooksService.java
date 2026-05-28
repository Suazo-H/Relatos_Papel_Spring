package com.unir.relatos.catalogue.service;

import com.unir.relatos.catalogue.controller.model.GetBooksResponseDto;
import com.unir.relatos.catalogue.controller.model.GetBookResponseDto;
import com.unir.relatos.catalogue.exception.BookNotFoundException;
import com.unir.relatos.catalogue.repository.predicate.BookJPARepository;
import com.unir.relatos.catalogue.repository.model.Book;
import com.unir.relatos.catalogue.utils.BookMapper;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetBooksService {

    private final BookJPARepository repository;
    private final BookMapper mapper;

    @Transactional(readOnly = true)
    public GetBooksResponseDto getBooks() {
        List<Book> books = repository.findAvailableBooks();
        return GetBooksResponseDto.builder()
                .books(mapper.asBookDtoList(books))
                .build();
    }

    @Transactional(readOnly = true)
    public GetBookResponseDto getBook(Integer bookId) {
        Optional<Book> book = repository.findById(bookId);
        return book.map(mapper::asGetBookResponseDto)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));
    }

    /**
     * Search books with combined filters using JPA Specifications.
     * All filters are optional and can be combined.
     */
    @Transactional(readOnly = true)
    public GetBooksResponseDto searchBooks(
            String title,
            String author,
            String genre,
            String isbn,
            Integer publishedYear,
            BigDecimal minRating,
            BigDecimal maxRating,
            Boolean visible) {

        Specification<Book> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Title filter (LIKE, case-insensitive)
            if (title != null && !title.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            // Author filter (LIKE, case-insensitive)
            if (author != null && !author.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
            }

            // Genre filter (LIKE, case-insensitive)
            if (genre != null && !genre.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("genre")), "%" + genre.toLowerCase() + "%"));
            }

            // ISBN filter (LIKE, case-insensitive)
            if (isbn != null && !isbn.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("isbn")), "%" + isbn.toLowerCase() + "%"));
            }

            // Published year filter (exact match)
            if (publishedYear != null) {
                predicates.add(cb.equal(root.get("publishedYear"), publishedYear));
            }

            // Rating range filter
            if (minRating != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("rating"), minRating));
            }
            if (maxRating != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("rating"), maxRating));
            }

            // Visibility filter
            if (visible != null) {
                predicates.add(cb.equal(root.get("visible"), visible));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<Book> books = repository.findAll(spec);
        return GetBooksResponseDto.builder()
                .books(mapper.asBookDtoList(books))
                .build();
    }
}
