package org.mimmey.exception.validation.chain;

import lombok.Getter;

/**
 * Исключение, сообщающее о том, что некоторые из индексов элементов-пересечений
 * выходят за границы списка значений
 * */
public class TooFewElementsException extends IllegalChainException {

    @Getter
    private final String label;

    public TooFewElementsException(String label) {
        super(String.format("Too few elements for the chain \"%s\" "
                + "(some crossing elements are out of range). "
                + "Make edges less or add new elements", label));
        this.label = label;
    }
}
