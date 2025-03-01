package com.unir.facets.service;

import com.unir.facets.controller.model.BooksQueryResponse;
import com.unir.facets.controller.model.CreateBookRequest;
import com.unir.facets.data.model.Book;

public interface BookService {

    BooksQueryResponse getBooks(String titulo, String autor, String categoria, Boolean aggregate);

    Book getBook(String bookId);

    Boolean removeBook(String bookId);

    Book createBook(CreateBookRequest request);
}
