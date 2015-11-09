package com.atoennis.walmartcodechallenge.data;

import android.content.Context;

import com.atoennis.walmartcodechallenge.BusProvider;
import com.atoennis.walmartcodechallenge.model.ProductWrapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.otto.Bus;

import java.io.IOException;
import java.io.InputStream;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

public class ProductService {

    private final Bus bus;

    private final ObjectMapper objectMapper;

    private final Context context;
    private final Retrofit retrofit;

    public ProductService(Context context) {
        this.bus = BusProvider.getInstance();
        this.context = context;

        // Dependency typically built and injected with Dagger 2
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://walmartlabs-test.appspot.com/_ah/api/walmart/v1")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        bus.register(this);
    }

    public void getProducts(GetProductsRequest request) {
        try {
            InputStream tempProductFile = context.getAssets().open(String.format("products%d.json", request.pageNumber));

            ProductWrapper productWrapper = objectMapper.readValue(tempProductFile, ProductWrapper.class);
            bus.post(new GetProductsResponse(productWrapper));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        ProductApiService productApiService = retrofit.create(ProductApiService.class);
//        productApiService.getProducts(context.getString(R.string.api_key), request.pageNumber,
//                request.pageSize)
//                .enqueue(new Callback<ProductWrapper>() {
//                    @Override
//                    public void onResponse(Response<ProductWrapper> response, Retrofit retrofit) {
//                        bus.post(new GetProductsResponse(response.body()));
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        Log.d(ProductService.class.getSimpleName(), "Failed to retrieve products!");
//                    }
//                });
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
