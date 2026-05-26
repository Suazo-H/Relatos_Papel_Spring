package com.unir.relatos.catalogue.controller.model;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "books"
})

@Getter
@Setter
@Builder

public class GetBooksResponseDto implements Serializable
{
    private final static long serialVersionUID = 6115802217081113365L;
    @JsonProperty("books")
    public List<BookDto> books;
}

