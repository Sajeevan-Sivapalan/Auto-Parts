package com.example.automate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automate.R;
import com.squareup.picasso.Picasso; // Import Picasso

import java.util.List;

import com.example.automate.model.Product;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {

    private List<Product> productList;
    private Context context;

    public CheckoutAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.checkout_item, parent, false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class CheckoutViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImageView;
        private TextView itemNameTextView;
        private TextView itemPriceTextView;
        private TextView itemCountTextView;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            itemCountTextView = itemView.findViewById(R.id.itemCountTextView);
        }

        public void bind(Product product) {
            // Load the product image using Picasso
            Picasso.get()
                    .load(product.getProductImage()) // Assuming Product class has getImageUrl method
                    .placeholder(R.drawable.image_not_found) // Placeholder image
                    .error(R.drawable.image_not_found) // Error image
                    .into(itemImageView);

            itemNameTextView.setText(product.getName()); // Assuming Product class has getName method
            itemPriceTextView.setText(String.format("Price: $%.2f", product.getPrice())); // Assuming Product class has getPrice method
            itemCountTextView.setText(String.format("Count: %d", 1)); // Set to 1 or product quantity if available
        }
    }
}
