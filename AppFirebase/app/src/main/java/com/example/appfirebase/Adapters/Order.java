package com.example.appfirebase.Adapters;

import java.io.Serializable;

public class Order{

    private int user;
    private String name, email, phone, address, productsnames, prodids, datums;
    private String productsprice;

    public Order(){
    }

    public Order(int user, String name, String email, String phone, String address, String productsnames, String prodids, String productsprice, String datums) {
        this.user = user;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.productsnames = productsnames;
        this.prodids = prodids;
        this.productsprice = productsprice;
        this.datums = datums;
    }



    public String getDatums() {
        return datums;
    }

    public void setDatums(String datums) {
        this.datums = datums;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProductsnames() {
        return productsnames;
    }

    public void setProductsnames(String productsnames) {
        this.productsnames = productsnames;
    }

    public String getProdids() {
        return prodids;
    }

    public void setProdids(String prodids) {
        this.prodids = prodids;
    }

    public String getProductsprice() {
        return productsprice;
    }

    public void setProductsprice(String productsprice) {
        this.productsprice = productsprice;
    }

}
