package com.unir.relatos.catalogue.service;

import com.unir.relatos.catalogue.controller.model.GetBooksResponseDto;
import com.unir.relatos.catalogue.controller.model.GetBookResponseDto;
import com.unir.relatos.catalogue.exception.BookNotFoundException;
import com.unir.relatos.catalogue.repository.predicate.BookJPARepository;
import com.unir.relatos.catalogue.repository.model.Book;
import com.unir.relatos.catalogue.utils.BookMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return book.map(
                b -> GetBookResponseDto.builder()
                        .id(b.getId())
                        .title(b.getTitle())
                        .author(b.getAuthor())
                        .genre(b.getGenre())
                        .price(b.getPrice().doubleValue())
                        .stock(b.getStock())
                        .isbn(b.getIsbn())
                        .rating(b.getRating())
                        .reviews(b.getReviews())
                        .pages(b.getPages())
                        .language(b.getLanguage())
                        .publisher(b.getPublisher())
                        .format(b.getFormat())
                        .synopsis(b.getSynopsis())
                        .publishedYear(b.getPublishedYear())
                        .image(b.getImage())
                        .visible(b.getVisible())
                        .build()
        ).orElseThrow(
                () -> new BookNotFoundException("Book not found with id: " + bookId));
    }
}
