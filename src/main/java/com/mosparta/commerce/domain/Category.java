package com.mosparta.commerce.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Category {
    private final String name;
    private final List<Product> products;

    public Category(String name, List<Product> products) {
        this.name = name;
        this.products = new ArrayList<>(products);
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return List.copyOf(products);
    }

    public Optional<Product> findProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();
    }

    public List<Product> findProductsByPrice(Integer minPrice, Integer maxPrice) {
        return products.stream()
                .filter(product -> {
                    Integer price = product.getPrice();
                    if (price > minPrice && price < maxPrice)
                        return true;
                    return false;
                })
                .toList();
    }

    public void addProduct(Product product) {
        List<String> productNames = products.stream().map(Product::getName).toList();

        if (productNames.contains(product.getName()))
            throw new IllegalArgumentException("이미 존재하는 상품명입니다");

        products.add(product);
    }

    public void deleteProduct(Product product) {
        products.remove(product);
    }
}
