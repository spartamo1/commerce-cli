package com.mosparta.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private final List<Product> products;

    public CommerceSystem(List<Product> products) {
        this.products = products;
    }

    private void printProductList() {
        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
        for (int i=0; i<products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%d. %-15s |%,10d원 | %s\n", i, p.getName(), p.getPrice(), p.getDescription());
        }
        System.out.printf("0. %-14s | 프로그램 종료\n", "종료");
    }

    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            printProductList();
            int num = Integer.parseInt(sc.next());
            if (num == 0)
                return;
        }
    }

}
