package com.atoennis.walmartcodechallenge.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    public final String productId;

    public final String name;

    public final String shortDescription;

    public final String longDescription;

    public final String price;

    public final String productImage;

    public final float reviewRating;

    public final int reviewCount;

    public final boolean inStock;

    public Product(@JsonProperty("productId") String productId,
                   @JsonProperty("name") String name,
                   @JsonProperty("shortDescription") String shortDescription,
                   @JsonProperty("longDescription") String longDescription,
                   @JsonProperty("price") String price,
                   @JsonProperty("productImage") String productImage,
                   @JsonProperty("reviewRating") float reviewRating,
                   @JsonProperty("reviewCount") int reviewCount,
                   @JsonProperty("inStock") boolean inStock) {
        this.productId = productId;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.price = price;
        this.productImage = productImage;
        this.reviewRating = reviewRating;
        this.reviewCount = reviewCount;
        this.inStock = inStock;
    }
}
