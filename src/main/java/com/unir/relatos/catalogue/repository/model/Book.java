package com.unir.relatos.catalogue.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "author", length = 255, nullable = false)
    private String author;

    @Column(name = "genre", length = 255)
    private String genre;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stock")
    private Integer stock;

    @Column(unique = true)
    private String isbn;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "reviews")
    private Integer reviews;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "language")
    private String language;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "format")
    private String format;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    @Column(name = "publishedyear")
    private Integer publishedYear;

    @Column(length = 1000)
    private String image;

    @Column(name = "visible")
    private Boolean visible;
}
