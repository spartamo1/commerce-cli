package com.mosparta.commerce.domain;

public class Customer {
    private String name;
    private String email;
    private CustomerGradeEnum grade;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public CustomerGradeEnum getGrade() {
        return grade;
    }

    public void setGrade(CustomerGradeEnum grade) {
        this.grade = grade;
    }
}
