package com.atoennis.walmartcodechallenge;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atoennis.walmartcodechallenge.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductActivityFragment extends Fragment {

    @Bind(R.id.product_list)
    RecyclerView productsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);

        ProductAdapter productAdapter = new ProductAdapter(new ArrayList<Product>());
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

    class ProductAdapter extends Adapter<ProductViewHolder> {
        private final List<Product> products;

        ProductAdapter(List<Product> products) {
            this.products = products;
        }

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.product_row, parent, false);

            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return products.size();
        }
    }

    static class ProductViewHolder extends ViewHolder {

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
