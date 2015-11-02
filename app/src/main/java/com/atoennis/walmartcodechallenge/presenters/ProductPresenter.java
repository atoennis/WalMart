package com.atoennis.walmartcodechallenge.presenters;

import com.atoennis.walmartcodechallenge.BusProvider;
import com.atoennis.walmartcodechallenge.data.ProductService;
import com.atoennis.walmartcodechallenge.data.ProductService.GetProductsRequest;
import com.atoennis.walmartcodechallenge.data.ProductService.GetProductsResponse;
import com.atoennis.walmartcodechallenge.model.Product;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class ProductPresenter {
    public interface ProductViewContract {

        void addProductsToList(List<Product> products);

    }

    public static class State {
        List<Product> products = new ArrayList<>();
        public int nextPageNumber = 1;
        public int pageNumber;
        public int pageSize = 12;
        public int totalProducts;
    }

    private ProductViewContract view;
    private State state;

    private final ProductService productService;

    public ProductPresenter(ProductService productService) {
        this.productService = productService;
        this.state = new State();
    }

    public void setView(ProductViewContract view) {
        this.view = view;
    }

    public void onResume() {
        BusProvider.getInstance().register(this);
        productService.getProducts(new GetProductsRequest(state.nextPageNumber, state.pageSize));
    }

    public void onPause() {
        BusProvider.getInstance().unregister(this);
    }

    public void onScrolledToBottomOfList() {
        if (state.pageNumber < state.nextPageNumber) {
            productService.getProducts(new GetProductsRequest(state.nextPageNumber, state.pageSize));
        }
    }

    @Subscribe
    public void handleGetProductsResponse(GetProductsResponse response) {
        state.totalProducts = response.productWrapper.totalProducts;
        state.pageNumber = response.productWrapper.pageNumber;
        state.nextPageNumber = generateNextPageNumber();

        state.products.addAll(response.productWrapper.products);

        view.addProductsToList(state.products);
    }

    int generateNextPageNumber() {
        int currentProducts = state.pageSize * state.nextPageNumber;

        return currentProducts < state.totalProducts ? state.pageNumber + 1 : state.pageNumber;
    }
}
