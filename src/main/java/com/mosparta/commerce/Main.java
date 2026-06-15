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

        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
        for (int i=0; i<products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%d. %-15s |%,10d원 | %s\n", i, p.getName(), p.getPrice(), p.getDescription());
        }
        System.out.printf("0. %-14s | 프로그램 종료", "종료");
    }
}