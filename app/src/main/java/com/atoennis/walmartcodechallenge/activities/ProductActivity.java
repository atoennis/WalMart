package com.atoennis.walmartcodechallenge.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.atoennis.walmartcodechallenge.R;
import com.atoennis.walmartcodechallenge.data.ProductService;
import com.atoennis.walmartcodechallenge.fragments.ProductFragment;
import com.atoennis.walmartcodechallenge.fragments.ProductFragment.ProductFragmentListener;
import com.atoennis.walmartcodechallenge.fragments.ProductPagerFragment;
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
        presenter.restoreState(savedInstanceState);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void displayProducts(List<Product> products) {
        getProductFragment().displayProducts(products);
    }

    @Override
    public void showProductDetails(List<Product> products, int productPos) {
        ProductPagerFragment fragment = ProductPagerFragment.buildFragment(products, productPos);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, fragment, TAG_PRODUCT_DETAIL_FRAGMENT)
                .addToBackStack(null)
                .commit();

        showUpNavigation();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.onBackPressed();
    }

    @Override
    public void hideUpNavigation() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void showUpNavigation() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private ProductFragment getProductFragment() {
        return (ProductFragment) getSupportFragmentManager().findFragmentByTag(TAG_PRODUCT_FRAGMENT);
    }
}
