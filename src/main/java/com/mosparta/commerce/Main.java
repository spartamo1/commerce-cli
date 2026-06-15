package com.mosparta.commerce;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();

        products.add(new Product("Galaxy S25", 1_200_000, "최신 안드로이드 스마트폰", 10));
        products.add(new Product("iPhone 16", 1_350_000, "Apple의 최신 스마트폰", 10));
        products.add(new Product("MacBook Pro", 2_400_000, "M3 칩셋이 탑재된 노트북", 10));
        products.add(new Product("AirPods Pro", 350_000, "노이즈 캔슬링 무선 이어폰", 10));

        CommerceSystem commerceSystem = new CommerceSystem(products);

        commerceSystem.start();
    }
}