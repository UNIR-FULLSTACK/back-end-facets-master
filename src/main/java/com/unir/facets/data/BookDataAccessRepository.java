package com.unir.facets.data;

import java.util.*;

import com.unir.facets.data.model.Book;
import com.unir.facets.controller.model.AggregationDetails;
import com.unir.facets.controller.model.BooksQueryResponse;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BookDataAccessRepository {

    @Value("${server.fullAddress:http://localhost:8088}")
    private String serverFullAddress;

    private final BookRepository bookRepository;
    private final ElasticsearchOperations elasticClient;

    private final String[] descripcionSearchFields = {"titulo", "titulo._2gram", "titulo._3gram"};

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Boolean delete(Book book) {
        bookRepository.delete(book);
        return Boolean.TRUE;
    }

    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    @SneakyThrows
    public BooksQueryResponse findBooks(String titulo, String autor, String categoria, Boolean aggregate) {

        BoolQueryBuilder querySpec = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(categoria)) {
            querySpec.must(QueryBuilders.termQuery("categoria", categoria));
        }

        if (!StringUtils.isEmpty(titulo)) {
            querySpec.must(QueryBuilders.matchQuery("titulo", titulo));
        }

        if (!StringUtils.isEmpty(autor)) {
            querySpec.must(QueryBuilders.matchQuery("autor", autor));
        }

        if (!querySpec.hasClauses()) {
            querySpec.must(QueryBuilders.matchAllQuery());
        }

        querySpec.must(QueryBuilders.termQuery("visible", true));

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(querySpec);

        if (aggregate) {
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("categoriaValues").field("categoria.keyword").size(1000));
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("autorValues").field("autor.keyword").size(1000));
            nativeSearchQueryBuilder.withMaxResults(0);
        }

        Query query = nativeSearchQueryBuilder.build();
        SearchHits<Book> result = elasticClient.search(query, Book.class);

        Map<String, List<AggregationDetails>> responseAggs = new HashMap<>();

        if (result.hasAggregations()) {
            Map<String, Aggregation> aggs = result.getAggregations().asMap();

            // Procesamiento de agregación por categoría
            if (aggs.containsKey("categoriaValues")) {
                ParsedStringTerms categoriaAgg = (ParsedStringTerms) aggs.get("categoriaValues");
                List<AggregationDetails> categoryDetails = new ArrayList<>();

                categoriaAgg.getBuckets().forEach(bucket ->
                        categoryDetails.add(new AggregationDetails(bucket.getKey().toString(), (int) bucket.getDocCount()))
                );

                responseAggs.put("categoriaValues", categoryDetails);
            }

            // Procesamiento de agregación por autor
            if (aggs.containsKey("autorValues")) {
                ParsedStringTerms autorAgg = (ParsedStringTerms) aggs.get("autorValues");
                List<AggregationDetails> authorDetails = new ArrayList<>();

                autorAgg.getBuckets().forEach(bucket ->
                        authorDetails.add(new AggregationDetails(bucket.getKey().toString(), (int) bucket.getDocCount()))
                );

                responseAggs.put("autorValues", authorDetails);
            }
        }

        return new BooksQueryResponse(result.getSearchHits().stream().map(SearchHit::getContent).toList(), responseAggs);
    }
}