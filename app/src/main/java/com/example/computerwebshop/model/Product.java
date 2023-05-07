package com.example.computerwebshop.model;

public class Product {
    private String _id;
    private String name;
    private String description;
    private String category;
    private int price;
    private String image;

    public Product() { }

    public Product(String name, String description, String category, int price, String image) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.image = image;
    }

    public String _getId() {
        return _id;
    }

    public void _setId(String _id) {
        this._id = _id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
