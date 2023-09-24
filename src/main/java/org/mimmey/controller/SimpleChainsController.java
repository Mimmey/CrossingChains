package org.mimmey.controller;

import lombok.extern.slf4j.Slf4j;
import org.mimmey.chain.Chain;
import org.mimmey.chain.SimpleChain;
import org.mimmey.exception.NoSuchChainException;
import org.mimmey.exception.validation.controller.IllegalChainAdditionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Slf4j
public class SimpleChainsController<T> implements ChainsController<T> {

    // Используем HashMap, т.к. у очередей нет какого-либо
    // натурального порядка (TreeMap не нужен)
    private final Map<String, Chain<T>> chains;

    public SimpleChainsController() {
        this.chains = new HashMap<>();
    }

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
     * @param chains очереди, которые необходимо поместить в контроллер
     * @throws IllegalChainAdditionException если количество ребер в очередях разное
     */
    public SimpleChainsController(Map<String, SimpleChain<T>> chains) {
        this.chains = new HashMap<>(chains.values()
                .stream()
                .map(SimpleChain::new)
                .collect(Collectors.toMap(Chain::getLabel, Function.identity())));
        checkChains();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addChain(Chain<T> chain) {
        Chain<T> chainCopy = new SimpleChain<>(chain);
        checkChain(chainCopy);
        chains.put(chainCopy.getLabel(), chainCopy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SimpleChain<T> getChain(String chainLabel) {
        Chain<T> chain = chains.get(chainLabel);

        if (chain == null) {
            throw new NoSuchChainException(chainLabel);
        }

        return new SimpleChain<>(chain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T push(String chainLabel, T pushingValue) {
        pushToChain(chainLabel, pushingValue);
        return popFromChainAndMoveIt(chainLabel);
    }

    protected void checkChain(Chain<T> chain) {
        int crossingElemsCount = chain.getCrossingElements().size();

        if (chains.values().stream().anyMatch(it -> it.getCrossingElements().size() != crossingElemsCount)) {
            log.warn("Edges for all of the chains in controller must contain equal number of edges");
            throw new IllegalChainAdditionException();
        }
    }

    protected void checkChains() {
        Optional<Chain<T>> any = chains.values().stream().findAny();

        if (any.isEmpty()) {
            return;
        }

        int crossingElemsCount = any.get().getCrossingElements().size();

        if (chains.values().stream().anyMatch(it -> it.getCrossingElements().size() != crossingElemsCount)) {
            log.warn("Edges for all of the chains in controller must contain equal number of edges");
            throw new IllegalChainAdditionException();
        }
    }

    protected void pushToChain(String chainLabel, T value) {
        try {
            chains.get(chainLabel).push(value);
        } catch (NullPointerException e) {
            log.warn("No chain with label \"{}\" available", chainLabel);
            throw new NoSuchChainException(chainLabel);
        }
    }

    protected T popFromChainAndMoveIt(String chainLabel) {
        try {
            Chain<T> poppingChain = chains.get(chainLabel);
            T poppedElem = poppingChain.popFirst();
            moveChain(poppingChain);
            return poppedElem;
        } catch (NullPointerException e) {
            log.warn("No chain with label {} available", chainLabel);
            throw new NoSuchChainException(chainLabel);
        }
    }

    protected void moveChain(Chain<T> movingChain) {
        List<T> newCrossingElements = movingChain.getCrossingElements();

        chains.values().stream()
                .filter(it -> !movingChain.getLabel().equals(it.getLabel()))
                .forEach(it -> it.replaceCrossingElements(
                        newCrossingElements
                ));
    }
}
