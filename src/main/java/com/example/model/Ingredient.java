package com.example.model;

import java.sql.Date;

public class Ingredient {
    private int id;
    private int userId;
    private String name;
    private int quantity;
    private Date expirationDate;

    // Constructor
    public Ingredient(int id, int userId, String name, int quantity, Date expirationDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
