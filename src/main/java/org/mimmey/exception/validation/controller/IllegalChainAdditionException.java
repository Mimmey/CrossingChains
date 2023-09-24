package org.mimmey.exception.validation.controller;

/**
 * Исключение, сообщающее о том, что значения количества ребер очередей в контроллере различаются
 * */
public class IllegalChainAdditionException extends IllegalControllerChangesException {

    public IllegalChainAdditionException() {
        super("Edges for all of the chains in controller must contain equal number of edges");
    }
}