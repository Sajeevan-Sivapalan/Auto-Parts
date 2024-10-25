package com.example.automate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.ProductCommentAdapter;
import model.CartManager;
import model.ProductCommentData;
import model.ProductImageSingleton;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageButton buttonBack, buttonCart;
    ImageView clickcartHome;
    private RecyclerView commentsRecyclerView;

    private Button buttonMinus, buttonPlus, updateReview, buttonAddToCart;
    private int quantity = 1;
    private double unitPrice;
    private int rating = 0;

    private TextView quantityTextView, productNameTextView, productPriceTextView, productDescriptionTextView, textAddToCart;

    private RatingBar ratingBar;
    private ImageView productImageView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int MAX_QUANTITY = 10;
    private TextView cart_count;

    private String productId, userId, name, productImage, categoryId, description, vendorId, createdAt, stockLastUpdated, productCategoryName, vendorName;
    private double price, averageRating;
    private int availableStock;
    private boolean isActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        cart_count = findViewById(R.id.cart_count);
        cart_count.setText(String.valueOf(CartManager.getInstance().getCartCount()));

        // Get data from Intent
        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");
        name = intent.getStringExtra("productName");
        productImage = intent.getStringExtra("productImage");
        categoryId = intent.getStringExtra("categoryId");
        description = intent.getStringExtra("description");
        price = intent.getDoubleExtra("price", 0.0);
        availableStock = intent.getIntExtra("availableStock", 0);
        isActive = intent.getBooleanExtra("isActive", false);
        vendorId = intent.getStringExtra("vendorId");
        createdAt = intent.getStringExtra("createdAt");
        stockLastUpdated = intent.getStringExtra("stockLastUpdated");
        productCategoryName = intent.getStringExtra("productCategoryName");
        vendorName = intent.getStringExtra("vendorName");
        averageRating = intent.getDoubleExtra("averageRating", 0.0);

        buttonBack = findViewById(R.id.buttonBack);
        clickcartHome = findViewById(R.id.clickcartHome);
        buttonCart = findViewById(R.id.buttonCart);
        updateReview = findViewById(R.id.updateReview);
        RatingBar ratingBarInput = findViewById(R.id.ratingBarInput);
        EditText editTextComment = findViewById(R.id.commentEditTextInput);
        buttonAddToCart = findViewById(R.id.buttonAddToCart); // Initialize the button
        buttonAddToCart.setOnClickListener(v -> addToCart()); // Set click listener

        ratingBarInput.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Here you can get the updated rating value
                Log.d("ProductDetailActivity", "User rating: " + rating);
            }
        });

        updateReview.setOnClickListener(v -> {
            // Get the rating and comment text
            int ratingIntValue = (int) ratingBarInput.getRating();
            String commentTextValue = editTextComment.getText().toString();

            // Check for empty comment if rating is 0
            if (ratingIntValue == 0) {
                editTextComment.setError("You must provide a rating before commenting.");
                return; // Exit if comment is empty
            }

            // Check for empty comment
            if (commentTextValue.isEmpty()) {
                editTextComment.setError("Comment is required.");
                return; // Exit if comment is empty
            }

            // Remove focus and hide the cursor from the EditText
            editTextComment.clearFocus();

        });


        // Handle Home click to load HomeFragment
        clickcartHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Handle Cart click to load Cart
        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for the back button
        buttonBack.setOnClickListener(v -> onBackPressed());

        // Initialize views
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        productNameTextView = findViewById(R.id.productNameTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView);
        textAddToCart = findViewById(R.id.textAddToCart);

        ratingBar = findViewById(R.id.ratingBar);
        productImageView = findViewById(R.id.productImageView);
        quantityTextView = findViewById(R.id.quantityTextView);

        // Fetch product details
        fetchProductDetails(productId);

        // Set up SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Reload product details
                refreshProductDetails();
                quantity = 1;
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);

        buttonMinus = findViewById(R.id.buttonMinus);
        buttonPlus = findViewById(R.id.buttonPlus);

        // Set up button listeners
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) { // Ensure quantity does not go below 1
                    quantity--;
                    quantityTextView.setText(String.valueOf(quantity));
                    updateTotalPrice();
                }
            }
        });

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quantity < MAX_QUANTITY) { // Ensure quantity does not exceed max balance
                    quantity++;
                    quantityTextView.setText(String.valueOf(quantity));
                    updateTotalPrice();
                }
            }
        });
    }


    private void refreshProductDetails() {
        // Optionally, you can add logic here to fetch updated product details
        fetchProductDetails(productId);
    }

    @Override
    public void onBackPressed() {
        // Check if the fragment manager has a back stack
        super.onBackPressed();
        Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private void fetchProductDetails(String productId) {

        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");
        name = intent.getStringExtra("productName");
        productImage = intent.getStringExtra("productImage");
        categoryId = intent.getStringExtra("categoryId");
        description = intent.getStringExtra("description");
        price = intent.getDoubleExtra("price", 0.0);
        availableStock = intent.getIntExtra("availableStock", 0);
        isActive = intent.getBooleanExtra("isActive", false);
        vendorId = intent.getStringExtra("vendorId");
        createdAt = intent.getStringExtra("createdAt");
        stockLastUpdated = intent.getStringExtra("stockLastUpdated");
        productCategoryName = intent.getStringExtra("productCategoryName");
        vendorName = intent.getStringExtra("vendorName");
        averageRating = intent.getDoubleExtra("averageRating", 0.0);


        // Update the UI with product details
        productNameTextView.setText(name);
        productPriceTextView.setText(String.format("%.2f", price));
        productDescriptionTextView.setText(description);
        MAX_QUANTITY = availableStock;
        unitPrice = price;

        // Set the rating bar
        ratingBar.setRating((float) averageRating);

        // Use Picasso to load the product image into the ImageView
        Picasso.get()
                .load(productImage)
                .placeholder(R.drawable.image_not_found)
                .error(R.drawable.image_not_found)
                .into(productImageView);

        // Set click listener to open full screen image
        productImageView.setOnClickListener(v -> {
            ProductImageSingleton.getInstance().setProductImage(productImage);
            Intent fullScreenIntent = new Intent(ProductDetailsActivity.this, FullScreenImageActivity.class);
            startActivity(fullScreenIntent);
        });

        updateTotalPrice();

        // After updating product details, fetch comments
        fetchComments();
    }

    private void updateTotalPrice() {
        // Calculate total price
        double totalPrice = quantity * unitPrice; // Calculate total price based on quantity and unit price
        textAddToCart.setText(String.format("LKR %.2f", totalPrice)); // Update the TextView with total price
    }

    private void fetchComments() {
        // Create a mock list of comments
        List<ProductCommentData> comments = new ArrayList<>();

        // Add mock comments to the list
        comments.add(new ProductCommentData("1", "user123", "Alice", "vendor456", "VendorCo", productId, "Product X", new Date(), 4, "Great product!"));
        comments.add(new ProductCommentData("2", "user456", "Bob", "vendor789", "TechWare", productId, "Product Y", new Date(), 5, "Excellent quality!"));
        comments.add(new ProductCommentData("3", "user789", "Charlie", "vendor123", "GadgetWorld", productId, "Product Z", new Date(), 3, "Average, but worth the price."));
        comments.add(new ProductCommentData("4", "user999", "Diana", "vendor456", "VendorCo", productId, "Product X", new Date(), 2, "Not satisfied with the product."));
        comments.add(new ProductCommentData("5", "user321", "Eve", "vendor456", "VendorCo", productId, "Product X", new Date(), 4, "Good value for money."));

        if (comments.isEmpty()) {
            // If no comments, show "No comments yet" and hide the RecyclerView
            findViewById(R.id.noCommentsTextView).setVisibility(View.VISIBLE);
            findViewById(R.id.commentsRecyclerView).setVisibility(View.GONE);
        } else {
            // Log and set up the adapter with the mock comments
            for (ProductCommentData comment : comments) {
                Log.d("CommentList", "Comment by " + comment.getUsername() + ": " + comment.getCommentText());
                Log.d("CommentList", "Rating: " + comment.getRating() + ", Date: " + comment.getDate());
                // Check if the comment belongs to the current user
                if (comment.getUserId().equals(userId)) {
                    Log.d("CommentList", "This comment belongs to the current user.");
                    // Get references to the RatingBar and EditText
                    RatingBar ratingBarInput = findViewById(R.id.ratingBarInput);
                    EditText commentEditTextInput = findViewById(R.id.commentEditTextInput);

                    // Set the RatingBar and EditText based on the comment's data
                    ratingBarInput.setRating(comment.getRating());
                    commentEditTextInput.setText(comment.getCommentText());
                    rating = comment.getRating();
                    Log.d("Comment rating 1", String.valueOf(rating));

                    // Check if rating is not equal to 0 to set the RatingBar as non-interactive
                    if (rating != 0) {
                        ratingBarInput.setIsIndicator(true);
                    } else {
                        ratingBarInput.setIsIndicator(false);
                    }
                }
            }

            // Set up the adapter
            ProductCommentAdapter productCommentAdapter = new ProductCommentAdapter(ProductDetailsActivity.this, comments);
            RecyclerView commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
            commentsRecyclerView.setAdapter(productCommentAdapter);

            // Show RecyclerView and hide the "No comments yet" message
            findViewById(R.id.noCommentsTextView).setVisibility(View.GONE);
            commentsRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    // Method to add product to cart
    private void addToCart() {
        Log.d("ProductDetailActivity", "Attempting to add product to cart for user: " + userId);
        Snackbar.make(findViewById(android.R.id.content), "Product Added to Cart!", Snackbar.LENGTH_SHORT).show();
    }
}

