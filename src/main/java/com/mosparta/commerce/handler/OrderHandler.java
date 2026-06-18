package com.mosparta.commerce.handler;

import com.mosparta.commerce.domain.Cart;
import com.mosparta.commerce.domain.Customer;
import com.mosparta.commerce.domain.CustomerGradeEnum;
import com.mosparta.commerce.exception.InvalidMenuInputException;

import java.util.Scanner;

public class OrderHandler {

    private final Scanner sc;
    private final Cart cart;
    private final Customer customer;

    public OrderHandler(Scanner sc, Cart cart, Customer customer) {
        this.cart = cart;
        this.customer = customer;
        this.sc = sc;
    }

    /**
     * 주문 로직
     */
    public void handleCartOrder() {
        System.out.println(cart);
        System.out.println("\n1. 주문 확정\t 2. 메인으로 돌아가기");

        int num = Integer.parseInt(sc.nextLine());

        if (num == 1) {
            System.out.println("고객 등급을 입력해주세요.");
            System.out.println(CustomerGradeEnum.getGradeDisplayText());

            num = Integer.parseInt(sc.nextLine());

            // 범위 검증
            if (num < 1 || num > CustomerGradeEnum.length)
                throw new InvalidMenuInputException(num, 1, CustomerGradeEnum.length);

            // TODO: 요구사항에 customer 의 grade 를 선택하게 한다?? - 질문하기
            CustomerGradeEnum customerGradeEnum = CustomerGradeEnum.fromIdx(num-1);
            customer.setGrade(customerGradeEnum);

            int totalPrice = cart.getTotalPrice();
            if (!cart.isAllInStock()) {
                System.out.println("재고가 부족한 상품이 존재합니다");
                return;
            }

            System.out.println("주문이 완료되었습니다!");
            System.out.println(customer.receiptFormat(totalPrice));
            cart.buy();
        } else if (num != 2) {
            throw new InvalidMenuInputException(num, 1, 2);
        }
    }

    public void handleCancelOrder() {
        cart.clear();
        System.out.println("주문 취소되었습니다");
    }

}
