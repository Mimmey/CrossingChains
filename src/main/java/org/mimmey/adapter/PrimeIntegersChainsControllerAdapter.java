package org.mimmey.adapter;

import org.mimmey.chain.Chain;
import org.mimmey.controller.PrimeIntegersChainsController;
import org.mimmey.exception.validation.NumberIsNotPrimeException;
import org.mimmey.exception.validation.chain.IllegalEdgesException;
import org.mimmey.exception.validation.chain.TooFewElementsException;

/**
 * Адаптер контроллера очередей простых чисел (под требуемый синтаксис)
 */
public class PrimeIntegersChainsControllerAdapter extends SimpleChainsControllerAdapter {

    public PrimeIntegersChainsControllerAdapter() {
        super.controller = new PrimeIntegersChainsController();
    }

    /**
     * <p>
     * Конструктор класса
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
     * @throws NumberIsNotPrimeException если в очередях есть не простые числа
     */
    public PrimeIntegersChainsControllerAdapter(Chain<Integer> chainA,
                                                Chain<Integer> chainB) {
        super.controller = new PrimeIntegersChainsController();
        controller.addChain(chainA);
        controller.addChain(chainB);
    }
}
