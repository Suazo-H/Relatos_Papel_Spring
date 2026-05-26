package com.unir.relatos.catalogue.utils;

import com.unir.relatos.catalogue.controller.model.BookDto;
import com.unir.relatos.catalogue.controller.model.GetBookResponseDto;
import com.unir.relatos.catalogue.controller.model.WriteBookRequestDto;
import com.unir.relatos.catalogue.exception.BookNotFoundException;
import com.unir.relatos.catalogue.repository.model.Book;
import com.unir.relatos.catalogue.repository.predicate.BookJPARepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class BookMapper {

    private final BookJPARepository bookJpaRepository;

    public BookMapper(BookJPARepository bookJpaRepository) {
        this.bookJpaRepository = bookJpaRepository;
    }

    public List<BookDto> asBookDtoList(List<Book> books){
        return books.stream()
                .map(book -> BookDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .genre(book.getGenre())
                        .price(book.getPrice().doubleValue())
                        .stock(book.getStock())
                        .isbn(book.getIsbn())
                        .rating(book.getRating())
                        .reviews(book.getReviews())
                        .pages(book.getPages())
                        .language(book.getLanguage())
                        .publisher(book.getPublisher())
                        .format(book.getFormat())
                        .synopsis(book.getSynopsis())
                        .publishedYear(book.getPublishedYear())
                        .image(book.getImage())
                        .visible(book.getVisible())
                        .build())
                .toList();
    }

    public GetBookResponseDto asGetBookResponseDto(Book book) {
        return GetBookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .price(book.getPrice().doubleValue())
                .stock(book.getStock())
                .isbn(book.getIsbn())
                .rating(book.getRating())
                .reviews(book.getReviews())
                .pages(book.getPages())
                .language(book.getLanguage())
                .publisher(book.getPublisher())
                .format(book.getFormat())
                .synopsis(book.getSynopsis())
                .publishedYear(book.getPublishedYear())
                .image(book.getImage())
                .visible(book.getVisible())
                .build();
    }

    public Book asBook(Integer bookId, WriteBookRequestDto bookDto) {
        Book oldBook = bookJpaRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + bookId + " not found."));
        return Book.builder()
                .id(bookId)
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .genre(bookDto.getGenre())
                .price(BigDecimal.valueOf(bookDto.getPrice()))
                .stock(bookDto.getStock())
                .isbn(bookDto.getIsbn())
                .rating(bookDto.getRating())
                .reviews(bookDto.getReviews())
                .pages(bookDto.getPages())
                .language(bookDto.getLanguage())
                .publisher(bookDto.getPublisher())
                .format(bookDto.getFormat())
                .synopsis(bookDto.getSynopsis())
                .publishedYear(bookDto.getPublishedYear())
                .image(bookDto.getImage())
                .visible(bookDto.getVisible())
                .build();
    }

    public Book asBook(GetBookResponseDto getBookResponseDto) {
        Book oldBook = bookJpaRepository.findById(getBookResponseDto.getId())
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + getBookResponseDto.getId() + " not found."));
        return Book.builder()
                .id(getBookResponseDto.getId())
                .title(getBookResponseDto.getTitle())
                .author(getBookResponseDto.getAuthor())
                .genre(getBookResponseDto.getGenre())
                .price(getBookResponseDto.getPrice() != null ? BigDecimal.valueOf(getBookResponseDto.getPrice()) : null)
                .stock(getBookResponseDto.getStock())
                .isbn(getBookResponseDto.getIsbn())
                .rating(getBookResponseDto.getRating())
                .reviews(getBookResponseDto.getReviews())
                .pages(getBookResponseDto.getPages())
                .language(getBookResponseDto.getLanguage())
                .publisher(getBookResponseDto.getPublisher())
                .format(getBookResponseDto.getFormat())
                .synopsis(getBookResponseDto.getSynopsis())
                .publishedYear(getBookResponseDto.getPublishedYear())
                .image(getBookResponseDto.getImage())
                .visible(getBookResponseDto.getVisible())
                .build();
    }

}
