package com.mosparta.commerce.exception;

public class InvalidMenuInputException extends RuntimeException {

    public InvalidMenuInputException(int input, int min, int max) {
        super("'%d'는 유효하지 않은 입력입니다. %d ~ %d 사이의 숫자를 입력해주세요.".formatted(input, min, max));
    }
}