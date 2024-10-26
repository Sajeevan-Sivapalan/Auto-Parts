package com.example.automate.model;

import java.util.Date;

/**
 * The ProductCommentData class represents a comment made by a user on a product or vendor.
 * It includes fields for the comment details, such as user ID, vendor ID, product ID,
 * rating, comment text, and the date the comment was made.
 */
public class ProductCommentData {
    // Fields representing the details of a product comment
    private String id;
    private String userId;
    private String username;
    private String vendorId;
    private String vendorName;
    private String productId;
    private String productName;
    private Date date;
    private int rating;
    private String commentText;

    /**
     * Constructor for initializing all fields
     */
    public ProductCommentData(String id, String userId, String username, String vendorId,
                              String vendorName, String productId, String productName,
                              Date date, int rating, String commentText) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.productId = productId;
        this.productName = productName;
        this.date = date;
        this.rating = rating;
        this.commentText = commentText;
    }

    // Getter methods for each field

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
