package com.atoennis.walmartcodechallenge.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductWrapper {
    public final List<Product> products;

    public final int totalProducts;

    public final int pageNumber;

    public final int pageSize;

    public final int status;

    public final String kind;

    public final String eTag;

    @JsonCreator
    public ProductWrapper(@JsonProperty("products") List<Product> products,
                          @JsonProperty("totalProducts") int totalProducts,
                          @JsonProperty("pageNumber") int pageNumber,
                          @JsonProperty("pageSize") int pageSize,
                          @JsonProperty("status") int status,
                          @JsonProperty("kind") String kind,
                          @JsonProperty("etag") String eTag) {
        this.products = products;
        this.totalProducts = totalProducts;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.status = status;
        this.kind = kind;
        this.eTag = eTag;
    }
}
