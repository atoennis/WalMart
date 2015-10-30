package com.atoennis.walmartcodechallenge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.atoennis.walmartcodechallenge.ProductFragment.ProductFragmentListener;
import com.atoennis.walmartcodechallenge.data.ProductService;
import com.atoennis.walmartcodechallenge.data.ProductService.GetProductsRequest;
import com.atoennis.walmartcodechallenge.data.ProductService.GetProductsResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity implements ProductFragmentListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private ProductService productService;  // Ideally would be injected by Dagger 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        Bus bus = new Bus();
        bus.register(this);
        productService = new ProductService(bus, this);
        productService.getProducts(new GetProductsRequest());

        setSupportActionBar(toolbar);
    }

    @Subscribe
    public void handleGetProductsResponse(GetProductsResponse response) {
        getProductFragment().displayProducts(response.products);
    }

    private ProductFragment getProductFragment() {
        return (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.product_fragment);
    }

    @Override
    public void onScrolledToBottomOfList() {
        Log.d(ProductActivity.class.getSimpleName(), "At bottom of list, yay!");
    }
}
