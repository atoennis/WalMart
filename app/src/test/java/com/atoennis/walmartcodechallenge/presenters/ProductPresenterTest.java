package com.atoennis.walmartcodechallenge.presenters;


import com.atoennis.walmartcodechallenge.data.ProductService;
import com.atoennis.walmartcodechallenge.data.ProductService.GetProductsRequest;
import com.atoennis.walmartcodechallenge.presenters.ProductPresenter.State;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

public class ProductPresenterTest {

    private ProductPresenter productPresenter;
    private State state;
    private ProductService productService;

    @Before
    public void setup() {
        productService = mock(ProductService.class);
        state = new State();
        productPresenter = new ProductPresenter(productService);
        productPresenter.state = state;
    }

    @Test
    public void testPageNumberShouldIncrease() {
        state.pageNumber = 1;
        state.pageSize = 30;
        state.totalProducts = 31;

        int nextPageNumber = productPresenter.generateNextPageNumber();
        Assert.assertEquals(2, nextPageNumber);
    }

    @Test
    public void testPageNumberShouldNotIncrease() {
        state.pageNumber = 1;
        state.pageSize = 30;
        state.totalProducts = 30;

        int nextPageNumber = productPresenter.generateNextPageNumber();
        Assert.assertEquals(1, nextPageNumber);
    }

    @Test
    public void testHandleScrollToBottomOfPageWithNoMoreProducts() {
        state.pageNumber = 1;
        state.nextPageNumber = 1;

        productPresenter.onScrolledToBottomOfList();

        verify(productService, times(0)).getProducts(any(GetProductsRequest.class));
    }

    @Test
    public void testHandleScrollToBottomOfPageWithMoreProducts() {
        state.pageNumber = 1;
        state.nextPageNumber = 2;
        state.pageSize = 30;

        productPresenter.onScrolledToBottomOfList();

        ArgumentCaptor<GetProductsRequest> argumentCaptor = ArgumentCaptor.forClass(GetProductsRequest.class);
        verify(productService, times(1)).getProducts(argumentCaptor.capture());

        GetProductsRequest getProductsRequest = argumentCaptor.getValue();
        Assert.assertEquals(1, getProductsRequest.pageNumber);
        Assert.assertEquals(30, getProductsRequest.pageSize);
    }
}
