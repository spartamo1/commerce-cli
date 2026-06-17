package com.mosparta.commerce;

import com.mosparta.commerce.domain.Cart;
import com.mosparta.commerce.domain.Category;
import com.mosparta.commerce.domain.Customer;
import com.mosparta.commerce.domain.Product;
import com.mosparta.commerce.exception.InvalidMenuInputException;
import com.mosparta.commerce.handler.AdminModeHandler;
import com.mosparta.commerce.handler.CartHandler;
import com.mosparta.commerce.handler.OrderHandler;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private static final int ADMIN_MODE_NUM = 6;

    private final List<Category> categoryList;
    private final Cart cart;
    private final Customer customer;
    private final Scanner sc = new Scanner(System.in);

    private final AdminModeHandler adminModeHandler;
    private final OrderHandler orderHandler;
    private final CartHandler cartHandler;

    public CommerceSystem(List<Category> categoryList, Cart cart, Customer customer) {
        this.categoryList = categoryList;
        this.cart = cart;
        this.customer = customer;

        this.adminModeHandler = new AdminModeHandler(sc, categoryList, cart);
        this.orderHandler = new OrderHandler(sc, cart, customer);
        this.cartHandler = new CartHandler(sc, cart);
    }

    private void printCategories() {
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        for (int i=0; i<categoryList.size(); i++) {
            System.out.println((i+1) + ". " + categoryList.get(i).getName());
        }
        System.out.printf("0. %-14s | 프로그램 종료\n", "종료");
        System.out.println(ADMIN_MODE_NUM + ". 관리자 모드");
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
        while (true) {
            try {
                // 카테고리 선택
                printCategories();
                int cartAllCount = cart.getAllCount();
                if (cartAllCount > 0) {
                    System.out.printf("""
                            
                            [ 주문 관리 ]
                            %d. 장바구니 확인    | 장바구니를 확인 후 주문합니다.
                            %d. 주문 취소       | 진행중인 주문을 취소합니다.
                            \n""", categoryList.size()+1, categoryList.size()+2);
                }
                int num = Integer.parseInt(sc.nextLine());

                // 범위 검증
                int maxNum = cartAllCount > 0 ? categoryList.size()+2 : categoryList.size();
                if (num < 0 || (num > maxNum && num != ADMIN_MODE_NUM))
                    throw new InvalidMenuInputException(num, 0, maxNum);

                if (num == 0)
                    return;

                if (num == categoryList.size()+1) {
                    orderHandler.handleCartOrder();
                    continue;
                }

                if (num == categoryList.size()+2) {
                    orderHandler.handleCancelOrder();
                    continue;
                }

                if (num == ADMIN_MODE_NUM) {
                    adminModeHandler.handle();
                    continue;
                }

                Category selectedCategory = categoryList.get(num-1);
                printProductList(selectedCategory);
                if (!cartHandler.handleCart(selectedCategory))
                    return;
            } catch (InvalidMenuInputException | IllegalStateException | IllegalArgumentException e) {
                if (e instanceof NumberFormatException)
                    System.out.println("숫자를 입력해주세요 " + e.getMessage());
                else
                    System.out.println(e.getMessage());
            }
        }
    }

}
