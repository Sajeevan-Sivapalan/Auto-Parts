package com.example.automate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automate.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.automate.model.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> productList;
    private Map<Product, Integer> productQuantities;
    private OnQuantityChangeListener quantityChangeListener;

    public CartAdapter(List<Product> productList, OnQuantityChangeListener quantityChangeListener) {
        this.productList = productList;
        this.quantityChangeListener = quantityChangeListener;
        productQuantities = new HashMap<>();

        for (Product product : productList) {
            productQuantities.put(product, 1);
        }
        notifyTotalPrice(); // Initialize total price
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = productList.get(position);
        int quantity = productQuantities.get(product);

        Picasso.get()
                .load(product.getProductImage())
                .placeholder(R.drawable.image_not_found)
                .error(R.drawable.image_not_found)
                .into(holder.itemImageView);

        holder.itemNameTextView.setText(product.getName());
        holder.itemPriceTextView.setText(String.format("Price: LKR %.2f", product.getPrice()));
        holder.itemCountChangeTextView.setText(String.valueOf(quantity)); // Update the count to display current quantity

        // Set OnClickListeners for Count Buttons
        holder.plusButton.setOnClickListener(v -> {
            int newQuantity = productQuantities.get(product) + 1;
            productQuantities.put(product, newQuantity);
            holder.itemCountChangeTextView.setText(String.valueOf(newQuantity));// Update the count text view
            notifyTotalPrice();
        });

        holder.minusButton.setOnClickListener(v -> {
            int newQuantity = productQuantities.get(product) - 1;
            if (newQuantity > 0) {
                productQuantities.put(product, newQuantity);
                holder.itemCountChangeTextView.setText(String.valueOf(newQuantity));// Update the count text view
                notifyTotalPrice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private void notifyTotalPrice() {
        double totalPrice = 0;
        for (Product product : productList) {
            int quantity = productQuantities.get(product);
            totalPrice += product.getPrice() * quantity;
        }
        if (quantityChangeListener != null) {
            quantityChangeListener.onQuantityChanged(totalPrice);
        }
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView, itemPriceTextView, itemCountChangeTextView;
        ImageView itemImageView;
        Button plusButton, minusButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            itemCountChangeTextView = itemView.findViewById(R.id.itemCountChangeTextView);
            plusButton = itemView.findViewById(R.id.plusButton);
            minusButton = itemView.findViewById(R.id.minusButton);
            itemImageView = itemView.findViewById(R.id.itemImageView);
        }
    }

    public interface OnQuantityChangeListener {
        void onQuantityChanged(double totalAmount);
    }
}
