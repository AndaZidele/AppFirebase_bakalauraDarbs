package com.example.appfirebase.Adapters;


public class Product {
    private int id;
    private boolean special_offer;
    private String name, price, description, image, category;

    public Product() {
    }

    public Product(int id, boolean special_offer, String name, String price, String description, String image, String category) {
        this.id = id;
        this.special_offer = special_offer;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    public boolean isSpecial_offer() {
        return special_offer;
    }

    public void setSpecial_offer(boolean special_offer) {
        this.special_offer = special_offer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
