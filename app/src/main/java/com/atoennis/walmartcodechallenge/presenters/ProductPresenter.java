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
        productService.getProducts(new GetProductsRequest());
    }

    public void onPause() {
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void handleGetProductsResponse(GetProductsResponse response) {
        state.products.addAll(response.products);

        view.addProductsToList(state.products);
    }
}
