package com.mosparta.commerce.domain;

public class Customer {
    private String name;
    private String email;
    private CustomerGradeEnum grade;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setGrade(CustomerGradeEnum grade) {
        this.grade = grade;
    }

    public record DiscountResponse(Integer discountRate, Integer discountPrice, Integer totalPrice) {}

    public DiscountResponse discount(Integer totalPrice) {
        Integer discountPrice = totalPrice * grade.getDiscountRate() / 100;
        Integer newTotalPrice = totalPrice - discountPrice;

        return new DiscountResponse(grade.getDiscountRate(), discountPrice, newTotalPrice);
    }

    public String receiptFormat(Integer totalPrice) {
        DiscountResponse newPrice = discount(totalPrice);

        return String.format(
                """
                할인 전 금액: %,d원\n
                %s 등급 할인(%d%%): -%,d원\n
                최종 결제 금액: %,d원\n
                """,
                totalPrice,
                grade, newPrice.discountRate, newPrice.discountPrice,
                newPrice.totalPrice
        );
    }
}
