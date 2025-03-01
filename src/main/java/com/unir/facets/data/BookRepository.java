package com.unir.facets.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.unir.facets.data.model.Book;

public interface BookRepository extends ElasticsearchRepository<Book, String> {

    List<Book> findByTitulo(String titulo);

    Optional<Book> findById(String id);

    Book save(Book book);

    void delete(Book book);

    List<Book> findAll();
}
