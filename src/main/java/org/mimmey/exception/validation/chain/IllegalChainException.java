package org.mimmey.exception.validation.chain;

/**
 * Исключение, сообщающее о том, что очередь проинициализирована некорректно
 * */
public abstract class IllegalChainException extends IllegalStateException {

    public IllegalChainException(String message) {
        super(message);
    }
}
