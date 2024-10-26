package com.example.automate.model;

/**
 * The CartManagerSingleton class implements the Singleton pattern
 * to provide a global point of access to a single instance of the cart item count.
 * This class allows for setting and getting the cart item count.
 */
public class CartManagerSingleton {
    private static CartManagerSingleton instance;
    private int cartCount;

    // Private constructor to prevent instantiation
    private CartManagerSingleton() {
        cartCount = 3; // Initialize cart count
    }

    // Public method to get the single instance of the class
    public static synchronized CartManagerSingleton getInstance() {
        if (instance == null) {
            instance = new CartManagerSingleton();
        }
        return instance;
    }

    // Method to set the cart count
    public void setCartCount(int count) {
        this.cartCount = count;
    }

    // Method to get the cart count
    public int getCartCount() {
        return cartCount;
    }

    // Method to increment the cart count by 1
    public void incrementCartCount() {
        cartCount++;
    }

    // Method to decrement the cart count by 1
    public void decrementCartCount() {
        if (cartCount > 0) {
            cartCount--;
        }
    }
}
