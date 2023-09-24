package org.mimmey.exception.validation;

import lombok.Getter;

/**
 * Исключение, сообщающее о том, что число, которое должно быть простым, не
 * является простым
 * */
public class NumberIsNotPrimeException extends IllegalStateException {

    @Getter
    private final int num;

    public NumberIsNotPrimeException(int num) {
        super(String.format("Number %s is not prime", num));
        this.num = num;
    }
}
