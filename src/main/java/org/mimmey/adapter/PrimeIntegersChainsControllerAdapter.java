package org.mimmey.adapter;

import org.mimmey.chain.Chain;
import org.mimmey.chain.SimpleChain;
import org.mimmey.controller.ChainsController;
import org.mimmey.controller.PrimeIntegersChainsController;
import org.mimmey.exception.validation.chain.IllegalEdgesException;
import org.mimmey.exception.validation.chain.TooFewElementsException;

/**
 * {@inheritDoc}
 */
public class PrimeIntegersChainsControllerAdapter extends SimpleChainsControllerAdapter {

    private final ChainsController<Integer> controller;

    /**
     * <p>
     * Конструктор класса.
     * </p>
     * <p>
     * Все поля класса заполняются при помощи копирования
     * значений аргументов конструктора для сохранения целостности данных при воздействии
     * на аргументы по ссылкам вне данного класса
     * </p>
     *
     * @param chainA очередь "А"
     * @param chainB очередь "В"
     * @throws IllegalEdgesException   если в списке длин ребер есть отрицательные длины
     * @throws TooFewElementsException если индексы пересечений выходят за границы списка элементов
     */
    public PrimeIntegersChainsControllerAdapter(Chain<Integer> chainA,
                                                Chain<Integer> chainB) {
        super();
        this.controller = new PrimeIntegersChainsController();
        controller.addChain(chainA);
        controller.addChain(chainB);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int pushA(int value) {
        return controller.push("A", value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int pushB(int value) {
        return controller.push("B", value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Chain<Integer> getChainA() {
        return new SimpleChain<>(controller.getChain("A"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Chain<Integer> getChainB() {
        return new SimpleChain<>(controller.getChain("B"));
    }
}
