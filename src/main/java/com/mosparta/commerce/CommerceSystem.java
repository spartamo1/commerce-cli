package com.mosparta.commerce;

import com.mosparta.commerce.exceptions.InvalidMenuInputException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CommerceSystem {

    private final List<Category> categoryList;
    private final Cart cart;
    private static final int ADMIN_MODE_NUM = 6;

    public CommerceSystem(List<Category> categoryList) {
        this.categoryList = categoryList;
        this.cart = new Cart();
    }

    /**
     * 장바구니 추가 로직
     */
    private boolean handleCategoryView(Scanner sc, Category category) {
        printProductList(category);
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

    /**
     * 주문 로직
     */
    private void handleCartOrder(Scanner sc) {
        System.out.println(cart);
        System.out.println("\n1. 주문 확정\t 2. 메인으로 돌아가기");

        int num = Integer.parseInt(sc.nextLine());

        if (num == 1) {
            if (!cart.isAllInStock()) {
                System.out.println("재고가 부족한 상품이 존재합니다");
                return;
            }
            System.out.println("주문이 완료되었습니다! 총 금액: " + String.format("%,d", cart.getTotalPrice()) + "원");
            cart.buy();
            System.out.println("\n\n");
        } else if (num != 2) {
            throw new InvalidMenuInputException(num, 1, 2);
        }
    }

    private void handleCancelOrder() {
        cart.clear();
        System.out.println("주문 취소되었습니다");
    }

    private void handleAdminModeSubActions() {

    }

    private void handleAdminMode(Scanner sc) {
        System.out.println("관리자 비밀번호를 입력해주세요:");

        int tryCnt = 0;
        boolean loginned = false;
        while (!loginned && tryCnt++ < 3) {
            String passwd = sc.nextLine();
            if (passwd.equals("admin123"))
                loginned = true;
            else
                System.out.println("비밀번호가 틀렸습니다");
        }

        if (!loginned)
            return;

        while (true) {
            System.out.println(
                    """
                    [ 관리자 모드 ]
                    1. 상품 추가
                    2. 상품 수정
                    3. 상품 삭제
                    4. 전체 상품 현황
                    0. 메인으로 돌아가기
                    """
            );

            int num = Integer.parseInt(sc.nextLine());
            if (num < 0 || num > 4)
                throw new InvalidMenuInputException(num, 0, 4);

            if (num == 0)
                return;

            if (num == 1) {
                // 상품추가
                System.out.println("어느 카테고리에 상품을 추가하시겠습니까?");
                for (int i=0; i<categoryList.size(); i++) {
                    System.out.println(i+1 + ". " + categoryList.get(i).getName());
                }
                num = Integer.parseInt(sc.nextLine());
                if (num < 1 || num > 3)
                    throw new InvalidMenuInputException(num, 1, 3);

                Category selectedCategory = categoryList.get(num-1);
                System.out.printf("[ %s 카테고리에 상품 추가 ]\n", selectedCategory.getName());

                System.out.print("상품명을 입력해주세요: ");
                String name = sc.nextLine();
                System.out.print("가격을 입력해주세요: ");
                Integer price = Integer.parseInt(sc.nextLine());
                System.out.print("상품 설명을 입력해주세요: ");
                String description = sc.nextLine();
                System.out.print("재고수량을 입력해주세요: ");
                Integer stock = Integer.parseInt(sc.nextLine());

                Product product = new Product(name, price, description, stock);

                System.out.println(product);
                System.out.println("위 정보로 상품을 추가하시겠습니까?");
                System.out.println("1. 확인\t 2. 취소");

                num = Integer.parseInt(sc.nextLine());
                if (num < 1 || num > 2)
                    throw new InvalidMenuInputException(num, 1, 2);

                selectedCategory.addProduct(product);
                System.out.println("상품이 성공적으로 추가되었습니다!");
            } else if (num == 2) {
                // 상품수정
                System.out.println("어느 카테고리에 상품을 수정하시겠습니까?");
                for (int i=0; i<categoryList.size(); i++) {
                    System.out.println(i+1 + ". " + categoryList.get(i).getName());
                }
                num = Integer.parseInt(sc.nextLine());
                if (num < 1 || num > 3)
                    throw new InvalidMenuInputException(num, 1, 3);

                Category selectedCategory = categoryList.get(num-1);
                System.out.printf("[ %s 카테고리에 상품 추가 ]\n", selectedCategory.getName());

                System.out.print("수정할 상품명을 입력해주세요: ");
                String name = sc.nextLine();

                Optional<Product> optionalProduct = selectedCategory.findProductByName(name);
                if (optionalProduct.isEmpty()) {
                    System.out.println("해당 상품이 없습니다");
                    continue;
                }

                Product selectedProduct = optionalProduct.orElseThrow();

                System.out.println("현재 상품 정보: " + selectedProduct);
                System.out.println("수정할 항목을 선택해주세요:\n" +
                        "1. 가격\n" +
                        "2. 설명\n" +
                        "3. 재고수량");

                num = Integer.parseInt(sc.nextLine());
                if (num < 1 || num > 3)
                    throw new InvalidMenuInputException(num, 1, 3);

                if (num == 1) {
                    // 가격 수정
                    int currentPrice = selectedProduct.getPrice();
                    System.out.printf("현재 가격: %,d원\n", currentPrice);
                    System.out.print("새로운 가격을 입력해주세요: ");

                    int newPrice = Integer.parseInt(sc.nextLine());
                    System.out.printf("%s의 가격이 %,d원 -> %,d원으로 수정되었습니다.\n", selectedProduct.getName(), currentPrice, newPrice);
                } else if (num == 2) {
                    // 설명 수정
                    String currentDesc = selectedProduct.getDescription();
                    System.out.printf("현재 설명: %s원\n", currentDesc);
                    System.out.print("새로운 설명을 입력해주세요: ");

                    String newDesc = sc.nextLine();
                    System.out.printf("%s의 설명이 %s원 -> %s원으로 수정되었습니다.\n", selectedProduct.getName(), currentDesc, newDesc);
                } else {
                    // 재고수량 수정
                    int currentStock = selectedProduct.getStock();
                    System.out.printf("현재 재고 수량: %d원\n", currentStock);
                    System.out.print("새로운 재고 수량을 입력해주세요: ");

                    int newStock = Integer.parseInt(sc.nextLine());
                    System.out.printf("%s의 재고 수량이 %d원 -> %d원으로 수정되었습니다.\n", selectedProduct.getName(), currentStock, newStock);
                }
            } else if (num == 3) {
                // 상품삭제
                System.out.println("어느 카테고리에 상품을 삭제하시겠습니까?");
                for (int i=0; i<categoryList.size(); i++) {
                    System.out.println(i+1 + ". " + categoryList.get(i).getName());
                }
                num = Integer.parseInt(sc.nextLine());
                if (num < 1 || num > 3)
                    throw new InvalidMenuInputException(num, 1, 3);

                Category selectedCategory = categoryList.get(num-1);
                System.out.printf("[ %s 카테고리에 상품 추가 ]\n", selectedCategory.getName());

                System.out.print("삭제할 상품명을 입력해주세요: ");
                String name = sc.nextLine();

                Optional<Product> optionalProduct = selectedCategory.findProductByName(name);
                if (optionalProduct.isEmpty()) {
                    System.out.println("해당 상품이 없습니다");
                    continue;
                }

                Product selectedProduct = optionalProduct.orElseThrow();
                selectedCategory.deleteProduct(selectedProduct);
            } else {
                // 전체 상품 현황
                for (Category category : categoryList) {
                    System.out.println("[ " + category.getName() + " ]");
                    category.getProducts().forEach(System.out::println);
                    System.out.println(); // for 가독성
                }
            }
        }
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
                int num = Integer.parseInt(sc.nextLine());

                // 범위 검증
                int maxNum = cartAllCount > 0 ? categoryList.size()+2 : categoryList.size();
                if (num < 0 || (num > maxNum && num != ADMIN_MODE_NUM))
                    throw new InvalidMenuInputException(num, 0, maxNum);

                if (num == 0)
                    return;

                if (num == categoryList.size()+1) {
                    handleCartOrder(sc);
                    continue;
                }

                if (num == categoryList.size()+2) {
                    handleCancelOrder();
                    continue;
                }

                if (num == ADMIN_MODE_NUM) {
                    handleAdminMode(sc);
                    continue;
                }

                Category selectedCategory = categoryList.get(num-1);
                if (!handleCategoryView(sc, selectedCategory))
                    return;
            } catch (InvalidMenuInputException | IllegalStateException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

}
