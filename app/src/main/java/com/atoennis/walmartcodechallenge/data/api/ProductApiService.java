package com.atoennis.walmartcodechallenge.data.api;

import com.atoennis.walmartcodechallenge.model.ProductWrapper;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ProductApiService {
    @GET("/_ah/api/walmart/v1/walmartproducts/{apiKey}/{pageNumber}/{pageSize}")
    Call<ProductWrapper> getProducts(@Path("apiKey") String apiKey,
                                     @Path("pageNumber") int pageNumber,
                                     @Path("pageSize") int pageSize);
}
