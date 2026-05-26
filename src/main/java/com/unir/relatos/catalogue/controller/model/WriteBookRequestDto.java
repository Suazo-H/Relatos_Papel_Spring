package com.unir.relatos.catalogue.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "title",
        "author",
        "genre",
        "price",
        "stock",
        "isbn",
        "rating",
        "reviews",
        "pages",
        "language",
        "publisher",
        "format",
        "synopsis",
        "publishedYear",
        "image"
})

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class WriteBookRequestDto implements Serializable{

    @JsonProperty("title")
    private String title;
    @JsonProperty("author")
    private String author;
    @JsonProperty("genre")
    private String genre;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("stock")
    private Integer stock;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("rating")
    private BigDecimal rating;
    @JsonProperty("reviews")
    private Integer reviews;
    @JsonProperty("pages")
    private Integer pages;
    @JsonProperty("language")
    private String language;
    @JsonProperty("publisher")
    private String publisher;
    @JsonProperty("format")
    private String format;
    @JsonProperty("synopsis")
    private String synopsis;
    @JsonProperty("publishedYear")
    private Integer publishedYear;
    @JsonProperty("image")
    private String image;
    @JsonProperty("visible")
    private Boolean visible;
    private static final long serialVersionUID = 1L;
}
