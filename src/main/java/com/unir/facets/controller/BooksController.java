package com.unir.facets.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.unir.facets.controller.model.BooksQueryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unir.facets.data.model.Book;
import com.unir.facets.controller.model.CreateBookRequest;
import com.unir.facets.service.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = {"http://localhost:3000", "https://spring-cloud-gateway-production-9d8a.up.railway.app", "https://relatos-de-papel-rho.vercel.app"})
@RestController
@RequiredArgsConstructor
@Slf4j
public class BooksController {

    private final BookService service;

    @GetMapping("/books")
    public ResponseEntity<BooksQueryResponse> getBooks(
            @RequestHeader Map<String, String> headers,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false, defaultValue = "false") Boolean aggregate) {

        log.info("headers: {}", headers);
        BooksQueryResponse books = service.getBooks(titulo, autor, categoria, aggregate);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable String bookId) {

        log.info("Request received for book {}", bookId);
        Book book = service.getBook(bookId);

        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {

        Boolean removed = service.removeBook(bookId);

        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody CreateBookRequest request) {

        Book createdBook = service.createBook(request);

        if (createdBook != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
