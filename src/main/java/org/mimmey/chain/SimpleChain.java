package org.mimmey.chain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mimmey.exception.ChainIsEmptyException;
import org.mimmey.exception.validation.chain.IllegalEdgesException;
import org.mimmey.exception.validation.chain.TooFewElementsException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Slf4j
public class SimpleChain<T> implements Chain<T> {

    @Getter
    private final String label;

    // Все конструкторы присваивают этому поле ArrayList.
    // Он был выбран, исходя из следующих предпосылок:
    //
    // 1. Для хранения нужно использовать Deque или List, т.к.
    // остальные контейнеры не предназначены для хранения упорядоченных
    // элементов без меток (или являются устаревшими, как Vector).
    // 2. Deque не подходит, т.к. в процессе работы нужно заменять
    // значения из середины.
    // 3. ArrayList и LinkedList навскидку отработали бы примерно одинаково,
    // т.к. ArrayList выиграл бы время при поиске элементов по индексам,
    // а LinkedList выиграл бы по удалению и вставкам.
    // 4. При тестировании на IntellijProfiler лучшие результаты показал
    // ArrayList
    private final List<T> values;

    // Здесь был использован List.copyOf(ArrayList),
    // т.к. изменять его по правилам бизнес-логики нельзя
    @Getter
    private final List<Integer> crossingIndices;

    /**
     * Конструктор класса. Все поля класса заполняются при помощи копирования
     * значений аргументов конструктора для сохранения целостности данных при воздействии
     * на аргументы по ссылкам вне данного класса
     *
     * @param beforeFirstCrossing количество элементов очереди до первого пересечения
     *                            с другими очередями
     * @param edgeLengths         список длин ребер (ребро — элементы от текущего пересечения
     *                            до следующего за ним невключительно) в порядке от головы к хвосту
     * @param values              значения элементов очереди в порядке от головы к хвосту
     * @param label               название очереди
     * @throws IllegalEdgesException   если в списке длин ребер есть отрицательные длины
     * @throws TooFewElementsException если индексы пересечений выходят за границы списка элементов
     */
    public SimpleChain(int beforeFirstCrossing, List<Integer> edgeLengths, List<T> values, String label) {
        this.values = new ArrayList<>(values);
        this.crossingIndices = getCrossingElementsIndices(beforeFirstCrossing, edgeLengths);
        this.label = label;
        checkEdgeLengths(edgeLengths);
        checkValuesCount();
    }

    /**
     * <p>
     * Конструктор класса. По умолчанию количество элементов до первого равно 0.
     * </p>
     * <p>
     * Все поля класса заполняются при помощи копирования
     * значений аргументов конструктора для сохранения целостности данных при воздействии
     * на аргументы по ссылкам вне данного класса
     *
     * @param edgeLengths список длин ребер (ребро — элементы от текущего пересечения
     *                    до следующего за ним невключительно) в порядке от голову к хвосту
     * @param values      значения элементов оереди в порядке от головы к хвосту
     * @param label       название очереди
     * @throws IllegalEdgesException   если в списке длин ребер есть отрицательные длины
     * @throws TooFewElementsException если индексы пересечений выходят за границы списка элементов
     */
    public SimpleChain(List<Integer> edgeLengths, List<T> values, String label) {
        this.values = new ArrayList<>(values);
        this.crossingIndices = getCrossingElementsIndices(0, edgeLengths);
        this.label = label;
        checkEdgeLengths(edgeLengths);
        checkValuesCount();
    }

    /**
     * <p>
     * Конструктор класса. По умолчанию количество элементов до первого равно 0.
     * </p>
     * <p>
     * Все поля класса заполняются при помощи копирования
     * значений аргументов конструктора для сохранения целостности данных при воздействии
     * на аргументы по ссылкам вне данного класса
     *
     * @param chain очередь, которую необходимо скопировать
     */
    public SimpleChain(Chain<T> chain) {
        this.label = chain.getLabel();
        this.crossingIndices = chain.getCrossingIndices();
        this.values = new ArrayList<>(chain.getValues());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getValues() {
        return List.copyOf(values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T popFirst() {
        try {
            return values.remove(0);
        } catch (NoSuchElementException e) {
            log.warn("Chain {} is empty, cannot pop first element", label);
            throw new ChainIsEmptyException(label);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void replaceCrossingElements(List<T> newCrossingElements) {
        List<T> newCrossingElementsCopy = new LinkedList<>(newCrossingElements);

        crossingIndices.forEach(it -> {
            values.remove(it.intValue());
            values.add(it, newCrossingElementsCopy.remove(0));
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getCrossingElements() {
        return crossingIndices.stream()
                .map(values::get)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void push(T elem) {
        values.add(elem);
    }

    private void checkEdgeLengths(List<Integer> edges) {
        if (edges.stream().mapToInt(Integer::intValue).anyMatch(it -> it < 0)) {
            log.warn("Edges for the chain \"{}\" "
                    + "must be non-negative", label);
            throw new IllegalEdgesException(label);
        }
    }

    private void checkValuesCount() {
        OptionalInt maxCrossingIndex = crossingIndices.stream().mapToInt(Integer::intValue).max();

        if (maxCrossingIndex.isPresent() && maxCrossingIndex.getAsInt() > values.size() - 1) {
            log.warn("Too few elements for the chain \"{}\" "
                    + "(some crossing elements are out of range). "
                    + "Make edges less or add new elements", label);
            throw new TooFewElementsException(label);
        }
    }

    private List<Integer> getCrossingElementsIndices(int beforeFirstCrossing, List<Integer> edgeLengths) {
        List<Integer> crossingIndices = new ArrayList<>();

        if (!edgeLengths.isEmpty()) {
            crossingIndices.add(beforeFirstCrossing);
        }

        for (int edgeLength : edgeLengths) {
            crossingIndices.add(crossingIndices.get(crossingIndices.size() - 1) + edgeLength + 1);
        }

        return List.copyOf(crossingIndices);
    }
}
