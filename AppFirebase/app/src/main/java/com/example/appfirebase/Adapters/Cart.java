package com.example.appfirebase.Adapters;


import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Cart implements Serializable {

    @Exclude

    private int prod, user, amount;
    private String name;
    private String price;

    public Cart() {
    }

    public Cart(int prod, int user, String name, String price, int amount) {
        this.prod = prod;
        this.user = user;
        this.price = price;
        this.name = name;
        this.amount = amount;
    }



    public int getProd() {
        return prod;
    }

    public void setProd(int prod) {
        this.prod = prod;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
