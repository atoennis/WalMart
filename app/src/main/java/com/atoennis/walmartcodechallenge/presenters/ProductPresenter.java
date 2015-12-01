package com.atoennis.walmartcodechallenge.presenters;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.atoennis.walmartcodechallenge.BusProvider;
import com.atoennis.walmartcodechallenge.data.ProductService;
import com.atoennis.walmartcodechallenge.data.ProductService.GetProductsRequest;
import com.atoennis.walmartcodechallenge.data.ProductService.GetProductsResponse;
import com.atoennis.walmartcodechallenge.model.Product;
import com.squareup.otto.Subscribe;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ProductPresenter {

    public interface ProductViewContract {

        void displayProducts(List<Product> products);

        void showProductDetails(List<Product> products, int productPosition);

        void hideUpNavigation();

        void showUpNavigation();
    }

    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Parcel
    public static class State {
        public List<Product> products = new ArrayList<>();
        public Product selectedProduct;
        public int nextPageNumber = 1;
        public int pageNumber;
        public int pageSize = 12;
        public int totalProducts = 0;
        public boolean retrievingProducts;
    }

    private ProductViewContract view;

    State state;
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
        if (state.selectedProduct == null) {
            if (state.products.size() <= 0) {
                getProducts(state.nextPageNumber, state.pageSize);
            }
            view.displayProducts(state.products);
        } else {
            view.showUpNavigation();
        }
    }

    public void onPause() {
        BusProvider.getInstance().unregister(this);
    }

    public void saveState(Bundle outState) {
        outState.putParcelable(EXTRA_STATE, Parcels.wrap(state));
    }

    public void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            state = Parcels.unwrap(savedInstanceState.getParcelable(EXTRA_STATE));
        }
    }

    public void onScrolledToBottomOfList() {
        if (state.pageNumber < state.nextPageNumber) {
            getProducts(state.nextPageNumber, state.pageSize);
        }
    }

    public void onProductClicked(@NonNull Product product) {
        state.selectedProduct = product;
        view.showProductDetails(state.products, findProductPosition(product));
    }

    public void onBackPressed() {
        state.selectedProduct = null;
        view.displayProducts(state.products);
        view.hideUpNavigation();
    }

    @Subscribe
    public void handleGetProductsResponse(GetProductsResponse response) {
        state.retrievingProducts = false;
        state.totalProducts = response.productWrapper.totalProducts;
        state.pageNumber = response.productWrapper.pageNumber;
        state.nextPageNumber = generateNextPageNumber();

        state.products.addAll(response.productWrapper.products);

        view.displayProducts(state.products);
    }

    private void getProducts(int nextPageNumber, int pageSize) {
        if (!state.retrievingProducts) {
            state.retrievingProducts = true;
            productService.getProducts(new GetProductsRequest(nextPageNumber, pageSize));
        }
    }

    int generateNextPageNumber() {
        int currentProducts = state.pageSize * state.pageNumber;

        return currentProducts < state.totalProducts ? state.pageNumber + 1 : state.pageNumber;
    }

    int findProductPosition(@NonNull Product product) {
        for (int pos = 0; pos < state.products.size(); pos++) {
            if (product.productId.equalsIgnoreCase(state.products.get(pos).productId)) {
                return pos;
            }
        }
        return 0;
    }

    boolean moreProductsAvailable() {
        return state.totalProducts <= 0 || state.products.size() < state.totalProducts;
    }
}
