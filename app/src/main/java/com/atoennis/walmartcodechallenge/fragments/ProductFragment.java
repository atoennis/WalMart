package com.atoennis.walmartcodechallenge.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Editable;
import android.text.Html;
import android.text.Html.TagHandler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atoennis.walmartcodechallenge.DividerItemDecoration;
import com.atoennis.walmartcodechallenge.R;
import com.atoennis.walmartcodechallenge.model.Product;
import com.squareup.picasso.Picasso;

import org.xml.sax.XMLReader;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductFragment extends Fragment {

    public interface ProductFragmentListener {

        void onScrolledToBottomOfList();

        void onProductClicked(Product product);

    }

    public static Fragment buildFragment() {
        return new ProductFragment();
    }

    @Bind(R.id.product_list) RecyclerView productsList;

    private ProductAdapter productAdapter;

    private ProductFragmentListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ProductFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);

        boolean tablet = getResources().getBoolean(R.bool.tablet);
        final LinearLayoutManager layoutManager;
        if (tablet) {
            layoutManager = new GridLayoutManager(getContext(), 3);
        } else {
            layoutManager = new LinearLayoutManager(getContext());
            productsList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        }

        productAdapter = new ProductAdapter();
        productsList.setHasFixedSize(true);
        productsList.setLayoutManager(layoutManager);
        productsList.setAdapter(productAdapter);
        productsList.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalItems = layoutManager.getItemCount();
                int lastVisibleItemPos = layoutManager.findLastVisibleItemPosition();

                if (lastVisibleItemPos + 1 >= totalItems) {
                    listener.onScrolledToBottomOfList();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    public void addProductsToList(List<Product> products) {
        productAdapter.displayProducts(products);
    }

    class ProductAdapter extends Adapter<ProductViewHolder> {

        private List<Product> products = Collections.emptyList();

        private final TagHandler tagHandler;

        ProductAdapter() {
            tagHandler = new ProductTagHandler();
        }

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.product_row, parent, false);

            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
            final Product product = products.get(position);

            holder.nameLabel.setText(product.name);
            holder.shortDescriptionLabel.setText(Html.fromHtml(product.shortDescription, null, tagHandler));
            holder.priceLabel.setText(product.price);
            Picasso.with(getContext())
                    .load(product.productImage)
                    .fit()
                    .centerInside()
                    .into(holder.productImage);

            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onProductClicked(product);
                }
            });
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        public void displayProducts(List<Product> products) {
            this.products = products;

            notifyDataSetChanged();
        }

        private class ProductTagHandler implements TagHandler {

            @Override
            public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
                if (tag.equalsIgnoreCase("li") && opening) {
                    output.append("\n");
                }
            }
        }
    }

    static class ProductViewHolder extends ViewHolder {
        @Bind(R.id.name) TextView nameLabel;
        @Bind(R.id.short_description) TextView shortDescriptionLabel;
        @Bind(R.id.price) TextView priceLabel;
        @Bind(R.id.image) ImageView productImage;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
