package com.mosparta.commerce;

import com.mosparta.commerce.exceptions.InvalidMenuInputException;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private final List<Category> categoryList;
    private final Cart cart;

    public CommerceSystem(List<Category> categoryList) {
        this.categoryList = categoryList;
        this.cart = new Cart();
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
                int num = Integer.parseInt(sc.next());

                // 범위 검증
                int maxNum = cartAllCount > 0 ? categoryList.size()+2 : categoryList.size();
                if (num < 0 || num > maxNum)
                    throw new InvalidMenuInputException(num, 0, maxNum);

                if (num == 0)
                    return;

                if (num == categoryList.size()+1) {
                    // 장바구니 확인 로직
                    System.out.println(cart);
                    System.out.println("\n1. 주문 확정\t 2. 메인으로 돌아가기");

                    num = Integer.parseInt(sc.next());

                    if (num == 1) {
                        if (!cart.isAllInStock()) {
                            System.out.println("재고가 부족한 상품이 존재합니다");
                            continue;
                        }

                        System.out.println("주문이 완료되었습니다! 총 금액: " + String.format("%,d", cart.getTotalPrice()) + "원");
                        cart.buy();
                        System.out.println("\n\n"); // 개행
                    } else if (num == 2) {
                        continue;
                    } else {
                        throw new InvalidMenuInputException(num, 1, 2);
                    }

                    continue;
                }

                if (num == categoryList.size()+2) {
                    // 주문 취소 로직
                    cart.clear();
                    System.out.println("주문 취소되었습니다");
                    continue;
                }

                // 상품 선택
                Category selectedCategory = categoryList.get(num-1);
                printProductList(selectedCategory);
                num = Integer.parseInt(sc.next());

                List<Product> selectedCategoryProducts = selectedCategory.getProducts();

                // 범위 검증
                maxNum = selectedCategoryProducts.size();
                if (num < 0 || num > maxNum)
                    throw new InvalidMenuInputException(num, 0, maxNum);

                if (num == 0)
                    return;

                // 장바구니 담기
                Product selectedProduct = selectedCategoryProducts.get(num-1);
                System.out.print("선택한 상품: ");
                System.out.println(selectedProduct + "\n");

                System.out.println(
                        "위 상품을 장바구니에 추가하시겠습니까?\n" +
                                "1. 확인\t2.취소"
                );
                num = Integer.parseInt(sc.next());

                if (num == 2) continue;
                if (num != 1) throw new InvalidMenuInputException(num, 1, 2);

                cart.addOneProduct(selectedProduct);
                System.out.println(selectedProduct.getName() + "가 장바구니에 추가되었습니다.");
            } catch (InvalidMenuInputException | IllegalStateException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

}
