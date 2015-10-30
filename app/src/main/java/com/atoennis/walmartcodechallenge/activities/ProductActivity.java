package com.atoennis.walmartcodechallenge.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.atoennis.walmartcodechallenge.R;
import com.atoennis.walmartcodechallenge.data.ProductService;
import com.atoennis.walmartcodechallenge.fragments.ProductFragment;
import com.atoennis.walmartcodechallenge.fragments.ProductFragment.ProductFragmentListener;
import com.atoennis.walmartcodechallenge.model.Product;
import com.atoennis.walmartcodechallenge.presenters.ProductPresenter;
import com.atoennis.walmartcodechallenge.presenters.ProductPresenter.ProductViewContract;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity implements ProductFragmentListener,
        ProductViewContract {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private ProductPresenter presenter; // Ideally would be injected and application scoped

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        presenter = new ProductPresenter(new ProductService(this));
        presenter.setView(this);

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    public void onScrolledToBottomOfList() {
        Log.d(ProductActivity.class.getSimpleName(), "At bottom of list, yay!");
    }

    @Override
    public void addProductsToList(List<Product> products) {
        getProductFragment().addProductsToList(products);
    }

    private ProductFragment getProductFragment() {
        return (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.product_fragment);
    }
}
