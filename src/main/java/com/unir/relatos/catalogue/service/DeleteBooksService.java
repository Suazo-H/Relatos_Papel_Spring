package com.unir.relatos.catalogue.service;

import com.unir.relatos.catalogue.exception.BookNotFoundException;
import com.unir.relatos.catalogue.repository.predicate.BookJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeleteBooksService {

    private final BookJPARepository bookJPARepository;

    @Transactional
    public void deleteBook(int bookId) {
        if(!bookJPARepository.existsById(bookId)) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found");
        }
        bookJPARepository.deleteById(bookId);
    }

}
