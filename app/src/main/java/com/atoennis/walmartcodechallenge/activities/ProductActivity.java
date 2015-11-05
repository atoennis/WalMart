package com.atoennis.walmartcodechallenge.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.atoennis.walmartcodechallenge.R;
import com.atoennis.walmartcodechallenge.data.ProductService;
import com.atoennis.walmartcodechallenge.fragments.ProductDetailFragment;
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

    private static final String TAG_PRODUCT_FRAGMENT = "TAG_PRODUCT_FRAGMENT";
    private static final String TAG_PRODUCT_DETAIL_FRAGMENT = "TAG_PRODUCT_DETAIL_FRAGMENT";

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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, ProductFragment.buildFragment(), TAG_PRODUCT_FRAGMENT)
                    .commit();
        }

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
        presenter.onScrolledToBottomOfList();
    }

    @Override
    public void onProductClicked(Product product) {
        presenter.onProductClicked(product);
    }

    @Override
    public void addProductsToList(List<Product> products) {
        getProductFragment().addProductsToList(products);
    }

    @Override
    public void showProductDetails(Product product) {
        ProductDetailFragment fragment = ProductDetailFragment.buildFragment(product);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, fragment, TAG_PRODUCT_DETAIL_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.onBackPressed();
    }

    private ProductFragment getProductFragment() {
        return (ProductFragment) getSupportFragmentManager().findFragmentByTag(TAG_PRODUCT_FRAGMENT);
    }
}
