package org.mimmey.exception;

import lombok.Getter;

/**
 * Исключение, сообщающее о том, что произошла попытка выполнить
 * над пустой очередью такие действия, которые требуют наличия в ней хотя бы
 * одного элемента
 * */
public class ChainIsEmptyException extends IllegalStateException {

    @Getter
    private final String label;

    public ChainIsEmptyException(String label) {
        super(String.format("Chain \"%s\" is empty", label));
        this.label = label;
    }
}
