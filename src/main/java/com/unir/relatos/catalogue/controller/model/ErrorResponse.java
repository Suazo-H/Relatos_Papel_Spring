package com.unir.relatos.catalogue.controller.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder

public class ErrorResponse implements Serializable {
    private final static long serialVersionUID = 1L;
    private String details;
}
