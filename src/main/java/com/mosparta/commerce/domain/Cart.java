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
        cartItems.forEach(Product::minusStock);
        cartItems.clear();
    }

    public void clear() {
        cartItems.clear();
    }

    /**
     * 삭제 v1. Product 객체가 동일(Product 클래스의 equals 메서드 참고)할 때만 삭제
     */
    public void deleteItem(Product product) {
        cartItems.remove(product);
    }

//    데이터 정합성 문제 때문에 Product equals 에서 name 만 비교하도록 수정함.
//    그럼 결국 위의 deleteItem (삭제 v1) 과 동일해짐. 아래코드는 의미가 없음.
//    /**
//     * 삭제 v2. Product 의 이름만 같아도 삭제
//     */
//    public void deleteItemByName(String name) {
//        cartItems.entrySet()
//                .removeIf(item -> item.getKey().getName().equals(name));
//    }

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
