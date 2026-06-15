package com.mosparta.commerce;

public class Product {
    private final String name;
    private final Integer price;
    private final String description;
    private Integer stock;

    public Product(String name, Integer price, String description, Integer stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "%-15s |%,10d원 | %s | 재고: %s개".formatted(name, price, description, stock);
    }

    public String toSummaryString() {
        return "%-15s |%,10d원 | %s".formatted(name, price, description);
    }
}
