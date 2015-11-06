package com.atoennis.walmartcodechallenge.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atoennis.walmartcodechallenge.R;
import com.atoennis.walmartcodechallenge.model.Product;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductPagerFragment extends Fragment {

    private static final String EXTRA_PRODUCTS = "EXTRA_PRODUCTS";
    public static final String EXTRA_PRODUCT_POSITION = "EXTRA_PRODUCT_POSITION";

    public static ProductPagerFragment buildFragment(List<Product> products, int productPos) {
        ProductPagerFragment fragment = new ProductPagerFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_PRODUCTS, Parcels.wrap(products));
        args.putInt(EXTRA_PRODUCT_POSITION, productPos);
        fragment.setArguments(args);

        return fragment;
    }

    @Bind(R.id.product_pager) ViewPager productPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_pager, container, false);

        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        if (args != null) {
            List<Product> products = Parcels.unwrap(args.getParcelable(EXTRA_PRODUCTS));
            final int productPos = args.getInt(EXTRA_PRODUCT_POSITION);

            productPager.setAdapter(new ProductPagerAdapter(products));
            productPager.setCurrentItem(productPos);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    class ProductPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Product> products;

        ProductPagerAdapter(List<Product> products) {
            super(getFragmentManager());
            this.products = products;
        }

        @Override
        public Fragment getItem(int position) {
            return ProductDetailFragment.buildFragment(products.get(position));
        }

        @Override
        public int getCount() {
            return products.size();
        }

    }
}
