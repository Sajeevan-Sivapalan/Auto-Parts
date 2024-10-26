package com.example.automate.model;

/**
 * The ProductImageSingleton class implements the Singleton pattern
 * to provide a global point of access to a single instance of the product image.
 * This class allows for setting and getting the product image.
 */
public class ProductImageSingleton {
    // Fields representing the details of an image
    private static ProductImageSingleton instance;
    private String productImage;

    // Private constructor to prevent instantiation
    private ProductImageSingleton() {}

    // Gets the singleton instance of ProductImageSingleton.
    public static ProductImageSingleton getInstance() {
        if (instance == null) {
            instance = new ProductImageSingleton();
        }
        return instance;
    }

    // Sets the product image.
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    // Gets the product image.
    public String getProductImage() {
        return productImage;
    }
}
