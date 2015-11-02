package com.atoennis.walmartcodechallenge.presenters;


import com.atoennis.walmartcodechallenge.data.ProductService;
import com.atoennis.walmartcodechallenge.presenters.ProductPresenter.State;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ProductPresenterTest {

    private ProductPresenter productPresenter;
    private State state;

    @Before
    public void setup() {
        ProductService productService = mock(ProductService.class);
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
}
