package com.mosparta.commerce;

public class Product {
    private String name;
    private Integer price;
    private String description;
    private Integer stock;

    public Product(String name, Integer price, String description, Integer stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
