package org.mimmey.exception.validation.chain;

import lombok.Getter;

/**
 * Исключение, сообщающее о том, что очередь имеет ребра с некорректными длинами
 * */
public class IllegalEdgesException extends IllegalChainException {

    @Getter
    private final String label;

    public IllegalEdgesException(String label) {
        super(String.format("Edges for the chain \"%s\" "
                + "are illegal", label));
        this.label = label;
    }
}