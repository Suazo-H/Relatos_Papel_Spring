package com.unir.relatos.orders.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO representing a book response from the Catalogue microservice.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {
    private Integer id;
    private String title;
    private String author;
    private String genre;
    private Double price;
    private Integer stock;
    private String isbn;
    private BigDecimal rating;
    private Integer reviews;
    private Integer pages;
    private String language;
    private String publisher;
    private String format;
    private String synopsis;
    private Integer publishedYear;
    private String image;
    private Boolean visible;
}
