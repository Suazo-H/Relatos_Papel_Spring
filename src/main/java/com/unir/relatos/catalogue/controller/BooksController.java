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

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor

public class BooksController {

    private final GetBooksService getBooksService;
    private final CreateBooksService createBooksService;
    private final DeleteBooksService deleteBooksService;
    private final ModifyBooksService modifyBooksService;

    @GetMapping("books")
    public ResponseEntity<GetBooksResponseDto>  getBooks() {
        return ResponseEntity.ok(getBooksService.getBooks());
    }

    @GetMapping("books/{bookId}")
    public ResponseEntity<GetBookResponseDto>  getBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(getBooksService.getBook(bookId.intValue()));
    }

    @PostMapping("books")
    public ResponseEntity<GetBookResponseDto>  createBook(@RequestBody WriteBookRequestDto request) {
        return ResponseEntity.ok(createBooksService.createBook(request));
    }

    @PutMapping("books/{bookId}")
    public ResponseEntity<GetBookResponseDto> updateBook(
       @PathVariable Long bookId,
       @RequestBody WriteBookRequestDto request){
            return ResponseEntity.ok(modifyBooksService.modifyBook(bookId.intValue(), request));
    }

    @PatchMapping("books/{bookId}")
    public ResponseEntity<GetBookResponseDto> updateBook(
            @PathVariable Long bookId,
            @RequestBody String jsonPart) {
        return ResponseEntity.ok(modifyBooksService.modifyBook(bookId.intValue(), jsonPart));
    }

    @DeleteMapping("books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        deleteBooksService.deleteBook(bookId.intValue());
        return ResponseEntity.noContent().build();
    }
}
