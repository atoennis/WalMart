package com.atoennis.walmartcodechallenge.data;

import android.content.Context;

import com.atoennis.walmartcodechallenge.BusProvider;
import com.atoennis.walmartcodechallenge.model.ProductWrapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.otto.Bus;

import java.io.IOException;
import java.io.InputStream;

public class ProductService {

    private final Bus bus;

    private final ObjectMapper objectMapper;

    private final Context context;

    public ProductService(Context context) {
        this.bus = BusProvider.getInstance();
        this.context = context;

        // Dependency typically built and injected with Dagger 2
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        bus.register(this);
    }

    public void getProducts(GetProductsRequest request) {
        try {
            InputStream tempProductFile = context.getAssets().open("products.json");

            ProductWrapper productWrapper = objectMapper.readValue(tempProductFile, ProductWrapper.class);
            bus.post(new GetProductsResponse(productWrapper));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class GetProductsRequest {
        public final int pageNumber;
        public final int pageSize;

        public GetProductsRequest(int pageNumber, int pageSize) {
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
        }
    }

    public static class GetProductsResponse {
        public final ProductWrapper productWrapper;

        public GetProductsResponse(ProductWrapper productWrapper) {
            this.productWrapper = productWrapper;
        }
    }
}
