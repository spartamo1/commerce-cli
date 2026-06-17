package com.mosparta.commerce.domain;

public enum CustomerGradeEnum {
    BRONZE("BRONZE", 0),
    SILVER("SILVER", 5),
    GOLD("GOLD", 10),
    PLATINUM("PLATINUM", 15);

    private final String grade;
    private final Integer discountRate;
    private static final CustomerGradeEnum[] values;
    public static final int length;
    private static final String GRADE_DISPLAY_TEXT; // 외부에서 출력하기 좋은 정돈된 텍스트

    static {
        values = CustomerGradeEnum.values();
        length = values.length;

        StringBuilder sb = new StringBuilder();

        int idx = 0;
        for (CustomerGradeEnum gradeEnum : values) {
            idx++;
            sb.append(String.format("%d. %-8s : %2d%% 할인\n", idx, gradeEnum.grade, gradeEnum.discountRate));
        }

        GRADE_DISPLAY_TEXT = sb.toString();
    }

    CustomerGradeEnum(String grade, Integer discountRate) {
        this.grade = grade;
        this.discountRate = discountRate;
    }

    public String getGrade() {
        return grade;
    }

    public Integer getDiscountRate() {
        return discountRate;
    }

    public static String getGradeDisplayText() {
        return GRADE_DISPLAY_TEXT;
    }

    public static CustomerGradeEnum fromIdx(int idx) {
        return values[idx];
    }

}
