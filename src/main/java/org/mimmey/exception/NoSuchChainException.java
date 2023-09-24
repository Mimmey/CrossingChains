package org.mimmey.exception;

import lombok.Getter;

/**
 * Исключение, сообщающее о том, что очереди с заданным названием в контроллере нет
 * */
public class NoSuchChainException extends IllegalArgumentException {

    @Getter
    private final String label;
    public NoSuchChainException(String label) {
        super(String.format("No chain with label \"%s\" available", label));
        this.label = label;
    }
}
