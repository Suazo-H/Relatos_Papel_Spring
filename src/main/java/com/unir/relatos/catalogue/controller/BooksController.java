package com.unir.relatos.catalogue.controller;

import com.unir.relatos.catalogue.controller.model.GetBooksResponseDto;
import com.unir.relatos.catalogue.controller.model.GetBookResponseDto;
import com.unir.relatos.catalogue.controller.model.WriteBookRequestDto;
import com.unir.relatos.catalogue.service.CreateBooksService;
import com.unir.relatos.catalogue.service.DeleteBooksService;
import com.unir.relatos.catalogue.service.GetBooksService;
import com.unir.relatos.catalogue.service.ModifyBooksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Libros", description = "API para gestión del catálogo de libros")
public class BooksController {

    private final GetBooksService getBooksService;
    private final CreateBooksService createBooksService;
    private final DeleteBooksService deleteBooksService;
    private final ModifyBooksService modifyBooksService;

    @Operation(
            summary = "Obtener libros",
            description = "Obtiene todos los libros disponibles o realiza una búsqueda combinada con filtros opcionales"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de libros obtenida correctamente",
                    content = @Content(schema = @Schema(implementation = GetBooksResponseDto.class)))
    })
    @GetMapping("books")
    public ResponseEntity<GetBooksResponseDto> getBooks(
            @Parameter(description = "Filtrar por título (búsqueda parcial)") @RequestParam(required = false) String title,
            @Parameter(description = "Filtrar por autor (búsqueda parcial)") @RequestParam(required = false) String author,
            @Parameter(description = "Filtrar por género (búsqueda parcial)") @RequestParam(required = false) String genre,
            @Parameter(description = "Filtrar por ISBN (búsqueda parcial)") @RequestParam(required = false) String isbn,
            @Parameter(description = "Filtrar por año de publicación") @RequestParam(required = false) Integer publishedYear,
            @Parameter(description = "Rating mínimo") @RequestParam(required = false) BigDecimal minRating,
            @Parameter(description = "Rating máximo") @RequestParam(required = false) BigDecimal maxRating,
            @Parameter(description = "Filtrar por visibilidad") @RequestParam(required = false) Boolean visible) {
        
        // If no filters provided, return all available books
        if (title == null && author == null && genre == null && isbn == null 
                && publishedYear == null && minRating == null && maxRating == null && visible == null) {
            return ResponseEntity.ok(getBooksService.getBooks());
        }
        
        // Search with combined filters
        return ResponseEntity.ok(getBooksService.searchBooks(
                title, author, genre, isbn, publishedYear, minRating, maxRating, visible));
    }

    @Operation(
            summary = "Obtener libro por ID",
            description = "Obtiene los detalles de un libro específico por su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado",
                    content = @Content(schema = @Schema(implementation = GetBookResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @GetMapping("books/{bookId}")
    public ResponseEntity<GetBookResponseDto> getBook(
            @Parameter(description = "ID del libro") @PathVariable Long bookId) {
        return ResponseEntity.ok(getBooksService.getBook(bookId));
    }

    @Operation(
            summary = "Crear libro",
            description = "Crea un nuevo libro en el catálogo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Libro creado correctamente",
                    content = @Content(schema = @Schema(implementation = GetBookResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("books")
    public ResponseEntity<GetBookResponseDto> createBook(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del libro a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = WriteBookRequestDto.class)))
            @RequestBody WriteBookRequestDto request) {
        return ResponseEntity.status(201).body(createBooksService.createBook(request));
    }

    @Operation(
            summary = "Actualizar libro (completo)",
            description = "Actualiza todos los campos de un libro existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = GetBookResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @PutMapping("books/{bookId}")
    public ResponseEntity<GetBookResponseDto> updateBook(
            @Parameter(description = "ID del libro") @PathVariable Long bookId,
            @RequestBody WriteBookRequestDto request) {
        return ResponseEntity.ok(modifyBooksService.modifyBook(bookId, request));
    }

    @Operation(
            summary = "Actualizar libro (parcial)",
            description = "Actualiza parcialmente un libro usando JSON Merge Patch"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = GetBookResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @PatchMapping("books/{bookId}")
    public ResponseEntity<GetBookResponseDto> patchBook(
            @Parameter(description = "ID del libro") @PathVariable Long bookId,
            @Parameter(description = "Campos a actualizar en formato JSON") @RequestBody String jsonPart) {
        return ResponseEntity.ok(modifyBooksService.modifyBook(bookId, jsonPart));
    }

    @Operation(
            summary = "Eliminar libro",
            description = "Elimina un libro del catálogo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Libro eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @DeleteMapping("books/{bookId}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID del libro") @PathVariable Long bookId) {
        deleteBooksService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
