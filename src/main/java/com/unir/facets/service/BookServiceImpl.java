package com.unir.facets.service;

import com.unir.facets.controller.model.BooksQueryResponse;
import com.unir.facets.data.BookDataAccessRepository;
import com.unir.facets.data.model.Book;
import com.unir.facets.controller.model.CreateBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDataAccessRepository repository;

    @Override
    public BooksQueryResponse getBooks(String titulo, String autor, String categoria, Boolean aggregate) {
        return repository.findBooks(titulo, autor, categoria, aggregate);
    }

    @Override
    public Book getBook(String bookId) {
        return repository.findById(bookId).orElse(null);
    }

    @Override
    public Boolean removeBook(String bookId) {
        Book book = repository.findById(bookId).orElse(null);
        if (book != null) {
            repository.delete(book);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Book createBook(CreateBookRequest request) {
        if (request != null && StringUtils.hasLength(request.getTitulo())
                && StringUtils.hasLength(request.getAutor())
                && StringUtils.hasLength(request.getCategoria())
                && request.getVisible() != null) {

            Book book = Book.builder()
                    .titulo(request.getTitulo())
                    .autor(request.getAutor())
                    .isbn(request.getIsbn())
                    .categoria(request.getCategoria())
                    .valoracion(request.getValoracion())
                    .fechapublica(request.getFechapublica())
                    .visible(request.getVisible())
                    .build();

            return repository.save(book);
        } else {
            return null;
        }
    }
}