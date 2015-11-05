package com.atoennis.walmartcodechallenge.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atoennis.walmartcodechallenge.R;
import com.atoennis.walmartcodechallenge.model.Product;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductDetailFragment extends Fragment {

    private static final String EXTRA_PRODUCT = "EXTRA_PRODUCT";

    public static ProductDetailFragment buildFragment(Product product) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_PRODUCT, Parcels.wrap(product));
        fragment.setArguments(args);

        return fragment;
    }

    @Bind(R.id.name) TextView nameField;
    @Bind(R.id.long_description) TextView longDescriptionField;
    @Bind(R.id.price) TextView priceField;
    @Bind(R.id.image) ImageView productImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        if (args != null) {
            Product product = Parcels.unwrap(args.getParcelable(EXTRA_PRODUCT));

            nameField.setText(product.name);
            longDescriptionField.setText(Html.fromHtml(product.longDescription).toString());
            priceField.setText(product.price);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
