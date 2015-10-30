package com.atoennis.walmartcodechallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atoennis.walmartcodechallenge.model.Product;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductFragment extends Fragment {

    @Bind(R.id.product_list) RecyclerView productsList;

    private ProductAdapter productAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);

        productAdapter = new ProductAdapter();
        productsList.setHasFixedSize(true);
        productsList.setLayoutManager(new LinearLayoutManager(getContext()));
        productsList.setAdapter(productAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    public void displayProducts(List<Product> products) {
        productAdapter.displayProducts(products);
    }

    class ProductAdapter extends Adapter<ProductViewHolder> {

        private List<Product> products = Collections.emptyList();

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.product_row, parent, false);

            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
            Product product = products.get(position);

            holder.nameLabel.setText(product.name);
            holder.shortDescriptionLabel.setText(product.shortDescription);
            holder.priceLabel.setText(product.price);
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        public void displayProducts(List<Product> products) {
            this.products = products;

            notifyDataSetChanged();
        }
    }

    static class ProductViewHolder extends ViewHolder {
        @Bind(R.id.name) TextView nameLabel;
        @Bind(R.id.short_description) TextView shortDescriptionLabel;
        @Bind(R.id.price) TextView priceLabel;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
