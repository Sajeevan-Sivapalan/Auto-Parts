package model;

public class CartManager {
    private static CartManager instance;
    private int cartCount;

    // Private constructor to prevent instantiation
    private CartManager() {
        cartCount = 0; // Initialize cart count
    }

    // Public method to get the single instance of the class
    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
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
