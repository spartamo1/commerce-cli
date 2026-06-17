package com.mosparta.commerce.domain;

import java.util.Objects;

public class Product {
    private final String name;
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

    public Integer getStock() {
        return stock;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public boolean isInStock(Integer count) {
        return count <= stock;
    }

    public void minusStock(Integer count) {
        if (count > stock) {
            throw new IllegalStateException("재고가 부족합니다.");
        }

        Integer prevStock = this.stock;
        stock -= count;
        System.out.printf("%s 재고가 %d개 -> %d개로 업데이트되었습니다.\n", name, prevStock, stock);
    }

    @Override
    public String toString() {
        return "%-15s |%,10d원 | %s | 재고: %s개".formatted(name, price, description, stock);
    }

    public String toSummaryString() {
        return "%-15s |%,10d원 | %s".formatted(name, price, description);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product product)) return false;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
