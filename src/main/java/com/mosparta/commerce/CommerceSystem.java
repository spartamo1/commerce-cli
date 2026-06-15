package com.mosparta.commerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private List<Category> categoryList;

    public CommerceSystem(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    private void printCategories() {
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        for (int i=0; i<categoryList.size(); i++) {
            System.out.println((i+1) + ". " + categoryList.get(i).getName());
        }
        System.out.printf("0. %-14s | 프로그램 종료\n", "종료");
    }

    private void printProductList(Category category) {
        System.out.printf("[ %s ]\n", category.getName());
        List<Product> products = category.getProducts();
        for (int i=0; i<products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%d. %s\n", i+1, p.toSummaryString());
        }
        System.out.printf("0. %-14s | 프로그램 종료\n", "종료");
    }

    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            printCategories();
            int num = Integer.parseInt(sc.next());
            if (num == 0)
                return;

            Category selectedCategory = categoryList.get(num-1);
            printProductList(selectedCategory);
            num = Integer.parseInt(sc.next());
            if (num == 0)
                return;
            System.out.print("선택한 상품: ");
            System.out.println(selectedCategory.getProducts().get(num-1) + "\n");
        }
    }

}
