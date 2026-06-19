package com.unir.relatos.catalogue.controller.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
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
        "image",
        "visible"
})
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class GetBookResponseDto implements Serializable {
    private final static long serialVersionUID = 1L;

    @JsonProperty("id")
    public Long id;
    @JsonProperty("title")
    public String title;
    @JsonProperty("author")
    public String author;
    @JsonProperty("genre")
    public String genre;
    @JsonProperty("price")
    public Double price;
    @JsonProperty("stock")
    public Integer stock;
    @JsonProperty("isbn")
    public String isbn;
    @JsonProperty("rating")
    public BigDecimal rating;
    @JsonProperty("reviews")
    public Integer reviews;
    @JsonProperty("pages")
    public Integer pages;
    @JsonProperty("language")
    public String language;
    @JsonProperty("publisher")
    public String publisher;
    @JsonProperty("format")
    public String format;
    @JsonProperty("synopsis")
    public String synopsis;
    @JsonProperty("cover")
    public String cover;
    @JsonProperty("publishedYear")
    public Integer publishedYear;
    @JsonProperty("image")
    public String image;
    @JsonProperty("visible")
    public Boolean visible;

}
