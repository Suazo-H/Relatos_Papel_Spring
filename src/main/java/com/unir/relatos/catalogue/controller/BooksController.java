package com.unir.relatos.catalogue.controller;

import com.unir.relatos.catalogue.controller.model.GetBooksResponseDto;
import com.unir.relatos.catalogue.controller.model.GetBookResponseDto;
import com.unir.relatos.catalogue.controller.model.WriteBookRequestDto;
import com.unir.relatos.catalogue.service.CreateBooksService;
import com.unir.relatos.catalogue.service.DeleteBooksService;
import com.unir.relatos.catalogue.service.GetBooksService;
import com.unir.relatos.catalogue.service.ModifyBooksService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BooksController {

    private final GetBooksService getBooksService;
    private final CreateBooksService createBooksService;
    private final DeleteBooksService deleteBooksService;
    private final ModifyBooksService modifyBooksService;

    /**
     * GET /api/v1/books - Get all books or search with filters
     * Supports combined search by: title, author, genre, isbn, publishedYear, rating, visible
     */
    @GetMapping("books")
    public ResponseEntity<GetBooksResponseDto> getBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer publishedYear,
            @RequestParam(required = false) BigDecimal minRating,
            @RequestParam(required = false) BigDecimal maxRating,
            @RequestParam(required = false) Boolean visible) {
        
        // If no filters provided, return all available books
        if (title == null && author == null && genre == null && isbn == null 
                && publishedYear == null && minRating == null && maxRating == null && visible == null) {
            return ResponseEntity.ok(getBooksService.getBooks());
        }
        
        // Search with combined filters
        return ResponseEntity.ok(getBooksService.searchBooks(
                title, author, genre, isbn, publishedYear, minRating, maxRating, visible));
    }

    /**
     * GET /api/v1/books/{bookId} - Get a specific book by ID
     */
    @GetMapping("books/{bookId}")
    public ResponseEntity<GetBookResponseDto> getBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(getBooksService.getBook(bookId));
    }

    /**
     * POST /api/v1/books - Create a new book
     */
    @PostMapping("books")
    public ResponseEntity<GetBookResponseDto> createBook(@RequestBody WriteBookRequestDto request) {
        return ResponseEntity.status(201).body(createBooksService.createBook(request));
    }

    /**
     * PUT /api/v1/books/{bookId} - Full update of a book
     */
    @PutMapping("books/{bookId}")
    public ResponseEntity<GetBookResponseDto> updateBook(
            @PathVariable Long bookId,
            @RequestBody WriteBookRequestDto request) {
        return ResponseEntity.ok(modifyBooksService.modifyBook(bookId, request));
    }

    /**
     * PATCH /api/v1/books/{bookId} - Partial update of a book
     */
    @PatchMapping("books/{bookId}")
    public ResponseEntity<GetBookResponseDto> patchBook(
            @PathVariable Long bookId,
            @RequestBody String jsonPart) {
        return ResponseEntity.ok(modifyBooksService.modifyBook(bookId, jsonPart));
    }

    /**
     * DELETE /api/v1/books/{bookId} - Delete a book
     */
    @DeleteMapping("books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        deleteBooksService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
