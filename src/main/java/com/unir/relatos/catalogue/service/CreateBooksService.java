package com.unir.relatos.catalogue.service;

import com.unir.relatos.catalogue.controller.model.WriteBookRequestDto;
import com.unir.relatos.catalogue.controller.model.GetBookResponseDto;
import com.unir.relatos.catalogue.repository.predicate.BookJPARepository;
import com.unir.relatos.catalogue.repository.model.Book;
import com.unir.relatos.catalogue.utils.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class CreateBooksService {
    private final BookJPARepository bookJPARepository;
    private final BookMapper bookMapper;

    @Transactional
    public GetBookResponseDto createBook(WriteBookRequestDto request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .genre(request.getGenre())
                .price((request.getPrice() != null ? BigDecimal.valueOf(request.getPrice()) : null))
                .stock(request.getStock())
                .isbn(request.getIsbn())
                .rating(request.getRating())
                .reviews(request.getReviews())
                .pages(request.getPages())
                .language(request.getLanguage())
                .publisher(request.getPublisher())
                .format(request.getFormat())
                .synopsis(request.getSynopsis())
                .publishedYear(request.getPublishedYear())
                .image(request.getImage())
                .visible(request.getVisible())
                .build();

        Book savedBook = bookJPARepository.save(book);
        return bookMapper.asGetBookResponseDto(savedBook);
    }
}
