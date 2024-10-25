package com.example.automate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import model.ProductImageSingleton;

public class FullScreenImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        ImageView fullScreenImageView = findViewById(R.id.fullScreenImageView);
        ImageView closeButton = findViewById(R.id.closeButton);

        String productImage = ProductImageSingleton.getInstance().getProductImage();

        // Use Picasso to load the product image into the ImageView
        Picasso.get()
                .load(productImage)
                .placeholder(R.drawable.image_not_found)
                .error(R.drawable.image_not_found)
                .into(fullScreenImageView);

        // Set click listener for the close button
        closeButton.setOnClickListener(v -> finish());
    }
}