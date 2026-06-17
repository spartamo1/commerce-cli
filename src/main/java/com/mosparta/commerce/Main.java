package com.mosparta.commerce;

import com.mosparta.commerce.domain.Cart;
import com.mosparta.commerce.domain.Category;
import com.mosparta.commerce.domain.Customer;
import com.mosparta.commerce.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // category 생성 + 각 category 의 product 생성후 매칭
        List<Product> elecProducts = new ArrayList<>();
        elecProducts.add(new Product("Galaxy S25", 1_200_000, "최신 안드로이드 스마트폰", 10));
        elecProducts.add(new Product("iPhone 16", 1_350_000, "Apple의 최신 스마트폰", 10));
        elecProducts.add(new Product("MacBook Pro", 2_400_000, "M3 칩셋이 탑재된 노트북", 10));
        elecProducts.add(new Product("AirPods Pro", 350_000, "노이즈 캔슬링 무선 이어폰", 10));
        Category elecCategory = new Category("전자제품", elecProducts);

        List<Product> clothesProducts = new ArrayList<>();
        Category clothesCategory = new Category("의류", clothesProducts); // TODO: 상품 추가

        List<Product> foodProducts = new ArrayList<>();
        Category foodCategory = new Category("식품", foodProducts); // TODO: 상품 추가

        // Cart 생성
        Cart cart = new Cart();

        // Customer 생성
        Customer customer = new Customer("tester1", "test@example.com");

        // cli 프로그램 시작
        CommerceSystem commerceSystem = new CommerceSystem(
                List.of(elecCategory, clothesCategory, foodCategory),
                cart,
                customer
        );
        commerceSystem.start();
    }
}