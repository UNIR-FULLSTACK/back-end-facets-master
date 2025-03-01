package com.unir.facets.data.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(indexName = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "titulo")
    private String titulo;

    @Field(type = FieldType.Text, name = "autor")
    private String autor;

    @Field(type = FieldType.Keyword, name = "isbn")
    private String isbn;

    @Field(type = FieldType.Text, name = "categoria")
    private String categoria;

    @Field(type = FieldType.Integer, name = "valoracion")
    private Integer valoracion;

    @Field(type = FieldType.Date, name = "fechapublica")
    private String fechapublica;

    @Field(type = FieldType.Boolean, name = "visible")
    private Boolean visible;
}