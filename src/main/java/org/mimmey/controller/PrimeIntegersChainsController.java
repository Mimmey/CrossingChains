package org.mimmey.controller;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mimmey.chain.Chain;
import org.mimmey.chain.SimpleChain;
import org.mimmey.exception.validation.NumberIsNotPrimeException;
import org.mimmey.exception.validation.chain.TooFewElementsException;
import org.mimmey.exception.validation.controller.IllegalChainAdditionException;

import java.math.BigInteger;
import java.util.Map;

/**
 * {@inheritDoc}
 * Данная реализация включает в себя проверку всех элементов
 * очередей на простоту
 */
@Slf4j
@NoArgsConstructor
public class PrimeIntegersChainsController extends SimpleChainsController<Integer> {

    /**
     * <p>
     * Конструктор класса. Все значения проверяются на простоту
     * </p>
     * <p>
     * Все поля класса заполняются при помощи копирования
     * значений аргументов конструктора для сохранения целостности данных при воздействии
     * на аргументы по ссылкам вне данного класса
     * </p>
     *
     * @param chains очереди, которые необходимо поместить в контроллер
     * @throws IllegalChainAdditionException если количество ребер в очередях разное
     * @throws TooFewElementsException если индексы пересечений выходят за границы списка элементов какой-либо из очередей
     * @throws NumberIsNotPrimeException     если очереди содержат не простые числа
     */
    public PrimeIntegersChainsController(Map<String, SimpleChain<Integer>> chains) {
        super(chains);

        chains.values().stream()
                .flatMap(chain -> chain.getValues().stream())
                .forEach(this::checkIfPrime);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Перед вставкой очередь проверяется на наличие непростых чисел
     * </p>
     */
    @Override
    public void addChain(Chain<Integer> chain) {
        Chain<Integer> chainCopy = new SimpleChain<>(chain);
        chainCopy.getValues().forEach(this::checkIfPrime);
        super.addChain(chainCopy);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Перед вставкой в очередь элемент проверяется на простоту
     * </p>
     */
    @Override
    public Integer push(String chainLabel, Integer pushingValue) {
        int pushingValueInt = pushingValue;
        checkIfPrime(pushingValueInt);
        pushToChain(chainLabel, pushingValueInt);
        return popFromChainAndMoveIt(chainLabel);
    }

    private void checkIfPrime(int num) {
        if (!BigInteger.valueOf(num).isProbablePrime(100)) {
            log.warn("Number {} is not prime", num);
            throw new NumberIsNotPrimeException(num);
        }
    }
}
