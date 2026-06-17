package com.mosparta.commerce.handler;

import com.mosparta.commerce.domain.Cart;
import com.mosparta.commerce.domain.Category;
import com.mosparta.commerce.domain.Product;
import com.mosparta.commerce.exception.InvalidMenuInputException;

import java.util.List;
import java.util.Scanner;

public class CartHandler {

    private final Scanner sc;
    private final Cart cart;

    public CartHandler(Scanner sc, Cart cart) {
        this.sc = sc;
        this.cart = cart;
    }

    /**
     * 장바구니 추가 로직
     */
    public boolean handleCart(Category category) {
        int num = Integer.parseInt(sc.nextLine());

        List<Product> products = category.getProducts();

        int maxNum = products.size();
        if (num < 0 || num > maxNum)
            throw new InvalidMenuInputException(num, 0, maxNum);

        if (num == 0)
            return false;

        Product selectedProduct = products.get(num-1);
        System.out.print("선택한 상품: ");
        System.out.println(selectedProduct + "\n");

        System.out.println("위 상품을 장바구니에 추가하시겠습니까?\n1. 확인\t2.취소");
        num = Integer.parseInt(sc.nextLine());

        if (num == 2) return true;
        if (num != 1) throw new InvalidMenuInputException(num, 1, 2);

        cart.addOneProduct(selectedProduct);
        System.out.println(selectedProduct.getName() + "가 장바구니에 추가되었습니다.");
        return true;
    }

}
