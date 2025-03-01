package com.unir.facets.controller.model;

import com.unir.facets.data.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BooksQueryResponse {

    private List<Book> books;
    private Map<String, List<AggregationDetails>> aggregations;
}