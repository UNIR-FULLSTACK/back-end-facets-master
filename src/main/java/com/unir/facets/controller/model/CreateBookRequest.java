package com.unir.facets.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

    private String titulo;
    private String autor;
    private String isbn;
    private String categoria;
    private Integer valoracion;
    private String fechapublica;
    private Boolean visible;
}