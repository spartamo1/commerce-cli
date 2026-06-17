package com.mosparta.commerce.domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private final Map<Product, Integer> cartItems = new HashMap<>();

    /**
     * 장바구니 담기
     */
    public void addOneProduct(Product product) {
        cartItems.merge(product, 1, Integer::sum);
    }

    public Integer getAllCount() {
        return cartItems.values().stream().mapToInt(Integer::intValue).sum();
    }

    public Integer getTotalPrice() {
        return cartItems.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public boolean isAllInStock() {
        return cartItems.entrySet().stream()
                .allMatch(entry -> entry.getKey().isInStock(entry.getValue()));
    }

    public void buy() {
        cartItems.forEach(Product::buy);
        cartItems.clear();
    }

    public void clear() {
        cartItems.clear();
    }

    public void deleteItem(Product product) {
        cartItems.remove(product);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ 장바구니 내역 ]\n");
        cartItems.forEach((key, val) -> {
            sb.append(key.toSummaryString()).append(" | 수량: ").append(val).append("개\n");
        });
        sb.append("[ 총 주문 금액 ]\n").append(String.format("%,d", getTotalPrice()));

        return sb.toString();
    }
}
