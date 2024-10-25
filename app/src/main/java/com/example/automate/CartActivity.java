package com.example.automate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.CartAdapter;
import model.Product;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnQuantityChangeListener {

    private TextView totalAmountTextView;
    private Button checkoutButton;
    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        totalAmountTextView = findViewById(R.id.totalAmountTextView);
        checkoutButton = findViewById(R.id.checkoutButton);
        buttonBack = findViewById(R.id.buttonBack);

        // Handle back click to load Cart
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Handle checkout click to load Cart
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("1", "Brake Pads", "https://cdn2.hubspot.net/hubfs/121786/auto-parts.jpg", "category1",
                "High-quality brake pads for superior stopping power.", 49.99, 100, true,
                "vendor1", "2024-01-01", "2024-01-10", "Brakes", "AutoCorp", 4.5));

        productList.add(new Product("2", "Oil Filter", "https://cdn2.hubspot.net/hubfs/121786/auto-parts.jpg", "category2",
                "Oil filter for various car models.", 19.99, 150, true,
                "vendor2", "2024-01-01", "2024-01-10", "Filters", "PartsCo", 4.2));

        productList.add(new Product("3", "Spark Plugs", "https://cdn2.hubspot.net/hubfs/121786/auto-parts.jpg", "category3",
                "Durable spark plugs for better ignition.", 9.99, 200, true,
                "vendor3", "2024-01-01", "2024-01-10", "Ignition", "SparkPro", 4.8));

        CartAdapter cartAdapter = new CartAdapter(productList, this);
        RecyclerView cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(cartAdapter);
    }

    @Override
    public void onQuantityChanged(double totalAmount) {
        totalAmountTextView.setText(String.format("Total: LKR %.2f", totalAmount));
    }
}
