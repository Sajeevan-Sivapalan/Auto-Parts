package com.example.automate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.automate.adapter.CheckoutAdapter;
import com.example.automate.model.Product;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private CheckoutAdapter checkoutAdapter;
    private List<Product> productList = new ArrayList<>();
    private TextView subtotalAmountTextView, totalAmountTextView;
    private Button payNowButton;
    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);

        payNowButton = findViewById(R.id.payNowButton);
        buttonBack = findViewById(R.id.buttonBack);

        // Handle back click to load Cart
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Handle checkout click to load Cart
        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckoutActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Initialize RecyclerView
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Example product list, replace with actual data retrieval logic
        productList.add(new Product("1", "Brake Pads", "https://neobrake.com/wp-content/uploads/2016/06/NeoBrake-Air-Disc-Brake-Pads-2.1.png", "category1", "High-quality brake pads for superior stopping power.", 49.99, 100, true, "vendor1", "2024-01-01", "2024-01-10", "Brakes", "AutoCorp", 4));
        productList.add(new Product("2", "Oil Filter", "https://res.cloudinary.com/knfilters-com/image/upload/c_lpad,dpr_2.0,f_auto,h_540,q_auto,w_540/v1/media/catalog/product/H/P/HP-1007-REV_2.jpg?_i=AB", "category2", "Oil filter for various car models.", 19.99, 150, true, "vendor2", "2024-01-01", "2024-01-10", "Filters", "PartsCo", 3));
        productList.add(new Product("3", "Spark Plugs", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHAvLF5RY893z8KAjXXdrlTn4JqSDAyqaW3w&s", "category3", "Durable spark plugs for better ignition.", 9.99, 200, true, "vendor3", "2024-01-01", "2024-01-10", "Ignition", "SparkPro", 5));

        checkoutAdapter = new CheckoutAdapter(productList);
        cartRecyclerView.setAdapter(checkoutAdapter);

        // Initialize summary views
        subtotalAmountTextView = findViewById(R.id.subtotalAmountTextView);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);

        // Calculate and display the summary
        updateSummary();

        // Handle back button click
        findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity and go back
            }
        });
    }

    // View total price
    private void updateSummary() {
        double subtotal = 0.0;
        for (Product product : productList) {
            subtotal += product.getPrice();
        }

        subtotalAmountTextView.setText(String.format("LKR %.2f", subtotal));
        totalAmountTextView.setText(String.format("LKR %.2f", subtotal)); // Assuming no additional fees
    }
}
