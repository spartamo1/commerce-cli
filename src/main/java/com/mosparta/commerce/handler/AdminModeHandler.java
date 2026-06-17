package com.mosparta.commerce.handler;

import com.mosparta.commerce.domain.Cart;
import com.mosparta.commerce.domain.Category;
import com.mosparta.commerce.domain.Product;
import com.mosparta.commerce.exception.InvalidMenuInputException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AdminModeHandler {

    private enum ProductActionEnum {
        ADD("추가"),
        MODIFY("수정"),
        DELETE("삭제");

        private final String key;

        ProductActionEnum(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }


    private final Scanner sc;
    private final List<Category> categoryList;
    private final Cart cart;

    public AdminModeHandler(Scanner sc, List<Category> categoryList, Cart cart) {
        this.sc = sc;
        this.categoryList = categoryList;
        this.cart = cart;
    }

    public boolean login() {
        System.out.println("관리자 비밀번호를 입력해주세요:");

        int tryCnt = 0;
        while (tryCnt++ < 3) {
            String passwd = sc.nextLine();
            if (passwd.equals("admin123"))
                return true;
            else
                System.out.println("비밀번호가 틀렸습니다");
        }

        return false;
    }

    public void handle() {
        if (!login())
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
                addProduct();
            } else if (num == 2) {
                modifyProduct();
            } else if (num == 3) {
                deleteProduct();
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

    private Category selectCategory(ProductActionEnum action) {
        System.out.printf("어느 카테고리에 상품을 %s하시겠습니까?\n", action.getKey());
        for (int i=0; i<categoryList.size(); i++) {
            System.out.println(i+1 + ". " + categoryList.get(i).getName());
        }
        int num = Integer.parseInt(sc.nextLine());
        if (num < 1 || num > categoryList.size())
            throw new InvalidMenuInputException(num, 1, categoryList.size());

        Category selectedCategory = categoryList.get(num-1);
        System.out.printf("[ %s 카테고리에 상품 %s ]\n", selectedCategory.getName(), action.getKey());

        return selectedCategory;
    }

    private Product selectProduct(ProductActionEnum action, Category selectedCategory) {
        System.out.print(action.getKey() + "할 상품명을 입력해주세요: ");
        String name = sc.nextLine();

        Optional<Product> optionalProduct = selectedCategory.findProductByName(name);
        if (optionalProduct.isEmpty()) {
            System.out.println("해당 상품이 없습니다");
            return null;
        }

        Product selectedProduct = optionalProduct.orElseThrow();
        System.out.println("현재 상품 정보: " + selectedProduct);

        return selectedProduct;
    }

    private void addProduct() {
        Category category = selectCategory(ProductActionEnum.ADD);

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

        int num = Integer.parseInt(sc.nextLine());
        if (num < 1 || num > 2)
            throw new InvalidMenuInputException(num, 1, 2);

        if (num == 1) {
            category.addProduct(product);
            System.out.println("상품이 성공적으로 추가되었습니다!");
        }
    }

    private void modifyProduct() {
        Category category = selectCategory(ProductActionEnum.MODIFY);
        Product product = selectProduct(ProductActionEnum.MODIFY, category);
        if (product == null)
            return;

        System.out.println("수정할 항목을 선택해주세요:\n" +
                "1. 가격\n" +
                "2. 설명\n" +
                "3. 재고수량");

        int num = Integer.parseInt(sc.nextLine());
        if (num < 1 || num > 3)
            throw new InvalidMenuInputException(num, 1, 3);

        if (num == 1) {
            modifyProductValue("가격", product.getName(), product::getPrice, product::setPrice, Integer::parseInt);
        } else if (num == 2) {
            modifyProductValue("설명", product.getName(), product::getDescription, product::setDescription, v->v);
        } else {
            modifyProductValue("재고수량", product.getName(), product::getStock, product::setStock, Integer::parseInt);
        }
    }

    private <T> void modifyProductValue(
            String key,
            String productName,
            Supplier<T> getter,
            Consumer<T> setter,
            Function<String, T> valueParser
    ) {
        T currentValue = getter.get();
        if (key.equals("가격")) {
            System.out.printf("현재 %s: %,d\n", key, (Integer) currentValue);
        } else {
            System.out.printf("현재 %s: %s\n", key, currentValue);
        }

        System.out.printf("새로운 %s을 입력해주세요: ", key);

        T newValue = valueParser.apply(sc.nextLine());
        setter.accept(newValue);

        if (key.equals("가격")) {
            System.out.printf("%s의 %s이 %,d원 -> %,d원으로 수정되었습니다.\n", productName, key, currentValue, (Integer) newValue);
        } else {
            System.out.printf("%s의 %s이 %s -> %s로 수정되었습니다.\n", productName, key, currentValue, newValue);
        }
    }

    private void deleteProduct() {
        Category category = selectCategory(ProductActionEnum.DELETE);
        Product product = selectProduct(ProductActionEnum.DELETE, category);
        if (product == null)
            return;

        System.out.println("위 정보로 상품을 삭제하시겠습니까?");
        System.out.println("1. 확인\t 2. 취소");

        int num = Integer.parseInt(sc.nextLine());
        if (num < 1 || num > 2)
            throw new InvalidMenuInputException(num, 1, 2);

        if (num == 1) {
            category.deleteProduct(product);
            cart.deleteItem(product);
            System.out.println("삭제되었습니다.");
        }
    }

}
