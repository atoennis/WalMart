package com.atoennis.walmartcodechallenge.data;

import android.content.Context;

import com.atoennis.walmartcodechallenge.model.Product;
import com.atoennis.walmartcodechallenge.model.ProductWrapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.otto.Bus;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ProductService {

    private final Bus bus;

    private final ObjectMapper objectMapper;

    private final Context context;

    public ProductService(Bus bus, Context context) {
        this.bus = bus;
        this.context = context;

        // Dependency typically built and injected with Dagger 2
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void getProducts(GetProductsRequest request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            InputStream tempProductFile = context.getAssets().open("products.json");

            ProductWrapper productWrapper = objectMapper.readValue(tempProductFile, ProductWrapper.class);
            bus.post(new GetProductsResponse(productWrapper.products));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class GetProductsRequest {
    }

    public static class GetProductsResponse {
        public final List<Product> products;

        public GetProductsResponse(List<Product> products) {
            this.products = products;
        }
    }
}
