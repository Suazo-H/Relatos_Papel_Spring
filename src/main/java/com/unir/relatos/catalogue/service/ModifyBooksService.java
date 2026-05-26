package com.unir.relatos.catalogue.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.unir.relatos.catalogue.controller.model.GetBookResponseDto;
import com.unir.relatos.catalogue.controller.model.WriteBookRequestDto;
import com.unir.relatos.catalogue.exception.BookNotFoundException;
import com.unir.relatos.catalogue.repository.predicate.BookJPARepository;
import com.unir.relatos.catalogue.repository.model.Book;
import com.unir.relatos.catalogue.utils.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ModifyBooksService {

    private final BookJPARepository bookJpaRepository;
    private final BookMapper bookMapper;
    private final ObjectMapper objectMapper;

    @Transactional
    public GetBookResponseDto modifyBook(Integer bookId, WriteBookRequestDto bookDto) {
        Book modifiedBook = bookMapper.asBook(bookId, bookDto);
        modifiedBook.setId(bookId);
        Book updatedBook = bookJpaRepository.save(modifiedBook);
        return bookMapper.asGetBookResponseDto(updatedBook);
    }

    @Transactional
    public GetBookResponseDto modifyBook(Integer bookId, String jsonPart) {
        GetBookResponseDto book = bookMapper
                .asGetBookResponseDto(
                        bookJpaRepository
                                .findById(bookId)
                                .orElseThrow(() -> new BookNotFoundException("Book with ID " + bookId + " not found.")));
        try {
            JsonNode patch = objectMapper.readTree(jsonPart);
            JsonNode actualBook = objectMapper.valueToTree(book);
            JsonMergePatch mergePatch = JsonMergePatch.fromJson(patch);

            JsonNode patchedBookNode = mergePatch.apply(actualBook);
            GetBookResponseDto patchedBook = objectMapper.treeToValue(patchedBookNode, GetBookResponseDto.class);
            patchedBook.setId(bookId);
            Book savedBook = bookJpaRepository.save(bookMapper.asBook(patchedBook));
            return bookMapper.asGetBookResponseDto(savedBook);
        } catch (JsonProcessingException | JsonPatchException e) {
            throw new RuntimeException("Error processing JSON patch", e);
        }
    }
}
