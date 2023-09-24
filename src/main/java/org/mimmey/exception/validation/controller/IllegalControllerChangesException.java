package org.mimmey.exception.validation.controller;

/**
 * Исключение, сообщающее о том, что контроллер проинициализирован неправильно или
 * что действия с ним привели к ошибке
 * */
public abstract class IllegalControllerChangesException extends IllegalStateException {

    public IllegalControllerChangesException(String message) {
        super(message);
    }
}
