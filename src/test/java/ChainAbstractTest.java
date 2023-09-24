import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mimmey.chain.SimpleChain;

import java.util.List;

public class ChainAbstractTest {

    protected ChainPushingExpectation getChainAExpectation() {
        return new ChainPushingExpectation(
                new SimpleChain<>(
                        List.of(4, 4),
                        List.of(1, 2, 3, 4, 5,
                                6, 7, 8, 9, 10,
                                11),
                        "A"),
                List.of(1, 6, 11),
                List.of(2, 3, 4, 5, 6,
                        7, 8, 9, 10, 11,
                        0),
                List.of(2, 7, 0)
        );
    }

    protected ChainPushingExpectation getChainBExpectation() {
        return new ChainPushingExpectation(
                new SimpleChain<>(
                        List.of(4, 4),
                        List.of(1, 200, 300, 400, 500,
                                6, 700, 800, 900, 1000,
                                11),
                        "B"),
                List.of(1, 6, 11),
                List.of(2, 200, 300, 400, 500,
                        7, 700, 800, 900, 1000,
                        0),
                List.of(2, 7, 0)
        );
    }

    protected ChainPushingExpectation getChainBLongExpectation() {
        return new ChainPushingExpectation(
                new SimpleChain<>(
                        List.of(5, 6),
                        List.of(1, 200, 300, 400, 500, 600,
                                6, 700, 800, 900, 1000, 1050, 1100,
                                11),
                        "B"),
                List.of(1, 6, 11),
                List.of(2, 200, 300, 400, 500, 600,
                        7, 700, 800, 900, 1000, 1050, 1100,
                        0),
                List.of(2, 7, 0)
        );
    }

    protected ChainPushingExpectation getChainALongAndTailsExpectation() {
        return new ChainPushingExpectation(
                new SimpleChain<>(
                        5,
                        List.of(4, 5),
                        List.of(-1, -2, -3, -4, -5,
                                1, 2, 3, 4, 5,
                                6, 7, 8, 9, 10, 100,
                                11, 12, 13, 14, 15, 16, 17, 18),
                        "A"),
                List.of(1, 6, 11),
                List.of(-2, -3, -4, -5, 1,
                        2, 3, 4, 5, 6,
                        7, 8, 9, 10, 100, 11,
                        12, 13, 14, 15, 16, 17, 18, 0),
                List.of(2, 7, 12)
        );
    }

    protected ChainPushingExpectation getChainBLongAndTailsExpectation() {
        return new ChainPushingExpectation(
                new SimpleChain<>(
                        2,
                        List.of(5, 6),
                        List.of(-100, -200,
                                1, 200, 300, 400, 500, 600,
                                6, 700, 800, 900, 1000, 1050, 1100,
                                11, 1200, 1300),
                        "B"),
                List.of(1, 6, 11),
                List.of(-100, -200,
                        2, 200, 300, 400, 500, 600,
                        7, 700, 800, 900, 1000, 1050, 1100,
                        12, 1200, 1300),
                List.of(2, 7, 12)
        );
    }

    protected ChainPushingExpectation getChainCLongAndTailsExpectation() {
        return new ChainPushingExpectation(
                new SimpleChain<>(
                        List.of(1, 0),
                        List.of(1, 2000,
                                6,
                                11, 1200, 1300)
                        ,
                        "C"),
                List.of(1, 6, 11),
                List.of(2, 2000,
                        7,
                        12, 1200, 1300),
                List.of(2, 7, 12)
        );
    }

    protected ChainPushingExpectation getEmptyChain() {
        return new ChainPushingExpectation(
                new SimpleChain<>(
                        List.of(),
                        List.of(),
                        "Empty"),
                List.of(),
                List.of(),
                List.of()
        );
    }

    protected SimpleChain<Integer> getChainATooFewElements() {
        return new SimpleChain<>(
                List.of(4, 10),
                List.of(1, 2, 3, 4, 5,
                        6, 7, 8, 9, 10),
                "A"
        );
    }

    protected SimpleChain<Integer> getChainANegativeEdges() {
        return new SimpleChain<>(
                List.of(4, -5),
                List.of(1, 2, 3, 4, 5,
                        6, 7, 8, 9, 10),
                "A"
        );
    }

    protected SimpleChain<Integer> getChainBTooMuchEdges() {
        return new SimpleChain<>(
                List.of(4, 4, 2),
                List.of(1, 200, 300, 400, 500,
                        6, 700, 800, 900, 1000,
                        11, 12, 13,
                        14),
                "B");
    }

    protected ChainPushingExpectation getChainAPrimeExpectation() {
        return new ChainPushingExpectation(
                new SimpleChain<>(
                        4,
                        List.of(4, 5),
                        List.of(2, 3, 5, 7,
                                11, 13, 17, 19, 23,
                                29, 31, 37, 41, 43, 47,
                                53, 59, 61, 67, 71, 73, 79, 83),
                        "A"),
                List.of(11, 29, 53),
                List.of(3, 5, 7, 11,
                        13, 17, 19, 23, 29,
                        31, 37, 41, 43, 47, 53,
                        59, 61, 67, 71, 73, 79, 83, 11),
                List.of(13, 31, 59)
        );
    }

    protected ChainPushingExpectation getChainBPrimeExpectation() {
        return new ChainPushingExpectation(
                new SimpleChain<>(
                        2,
                        List.of(1, 2),
                        List.of(89, 97,
                                11, 103,
                                29, 109, 113,
                                53, 131, 137),
                        "B"),
                List.of(11, 29, 53),
                List.of(89, 97,
                        13, 103,
                        31, 109, 113,
                        59, 131, 137),
                List.of(13, 31, 59)
        );
    }

    @Getter
    @AllArgsConstructor
    protected static class ChainPushingExpectation {
        private SimpleChain<Integer> startingChain;
        private List<Integer> beforePushingCrossingElements;
        private List<Integer> afterPushingChainValues;
        private List<Integer> afterPushingCrossingElements;
    }
}
