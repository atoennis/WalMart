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

import com.atoennis.walmartcodechallenge.ListTagHandler;
import com.atoennis.walmartcodechallenge.R;
import com.atoennis.walmartcodechallenge.model.Product;
import com.squareup.picasso.Picasso;

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
    @Bind(R.id.price) TextView priceField;
    @Bind(R.id.image) ImageView productImage;
    @Bind(R.id.in_stock_label) TextView inStockLabel;
    @Bind(R.id.description) TextView description;
    @Bind(R.id.short_description) TextView shortDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        if (args != null) {
            Product product = Parcels.unwrap(args.getParcelable(EXTRA_PRODUCT));

            nameField.setText(product.name);
            Picasso.with(getContext())
                    .load(product.productImage)
                    .fit()
                    .centerInside()
                    .into(productImage);
            priceField.setText(product.price);
            inStockLabel.setText(product.inStock ? getString(R.string.in_stock_label)
                    : getString(R.string.out_of_stock_label));

            shortDescription.setText(Html.fromHtml(product.shortDescription, null, new ListTagHandler("\n")));
            description.setText(Html.fromHtml(product.longDescription, null, new ListTagHandler("\n")));
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
