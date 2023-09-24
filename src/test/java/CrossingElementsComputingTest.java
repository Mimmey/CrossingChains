import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mimmey.adapter.ChainsControllerAdapter;
import org.mimmey.adapter.SimpleChainsControllerAdapter;
import org.mimmey.controller.ChainsController;
import org.mimmey.controller.SimpleChainsController;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CrossingElementsComputingTest extends ChainAbstractTest {

    private ChainsControllerAdapter adapter;

    @Test
    @DisplayName("crossing elements: getting when all edges are equal and there are no tails")
    public void getCrossingElements_EqualEdgesNoTails_True() {
        ChainPushingExpectation expectationA = getChainAExpectation();
        ChainPushingExpectation expectationB = getChainBExpectation();

        adapter = new SimpleChainsControllerAdapter(
                expectationA.getStartingChain(),
                expectationB.getStartingChain()
        );

        assertEquals(expectationA.getBeforePushingCrossingElements(), adapter.getChainA().getCrossingElements());
        assertEquals(expectationB.getBeforePushingCrossingElements(), adapter.getChainB().getCrossingElements());

        adapter.pushA(0);

        assertEquals(expectationA.getAfterPushingCrossingElements(), adapter.getChainA().getCrossingElements());
        assertEquals(expectationB.getAfterPushingCrossingElements(), adapter.getChainB().getCrossingElements());
    }

    @Test
    @DisplayName("crossing elements: getting when edges are non-equal and there are not tails")
    public void getCrossingElements_NonEqualEdgesNoTails_True() {
        ChainPushingExpectation expectationA = getChainAExpectation();
        ChainPushingExpectation expectationB = getChainBLongExpectation();

        adapter = new SimpleChainsControllerAdapter(
                expectationA.getStartingChain(),
                expectationB.getStartingChain()
        );

        assertEquals(expectationA.getBeforePushingCrossingElements(), adapter.getChainA().getCrossingElements());
        assertEquals(expectationB.getBeforePushingCrossingElements(), adapter.getChainB().getCrossingElements());

        adapter.pushA(0);

        assertEquals(expectationA.getAfterPushingCrossingElements(), adapter.getChainA().getCrossingElements());
        assertEquals(expectationB.getAfterPushingCrossingElements(), adapter.getChainB().getCrossingElements());
    }


    @Test
    @DisplayName("crossing elements: getting when edges are non-equal and there are tails")
    public void getCrossingElements_NonEqualEdgesAndTails_True() {
        ChainPushingExpectation expectationA = getChainALongAndTailsExpectation();
        ChainPushingExpectation expectationB = getChainBLongAndTailsExpectation();

        adapter = new SimpleChainsControllerAdapter(
                expectationA.getStartingChain(),
                expectationB.getStartingChain()
        );

        assertEquals(expectationA.getBeforePushingCrossingElements(), adapter.getChainA().getCrossingElements());
        assertEquals(expectationB.getBeforePushingCrossingElements(), adapter.getChainB().getCrossingElements());

        adapter.pushA(0);

        assertEquals(expectationA.getAfterPushingCrossingElements(), adapter.getChainA().getCrossingElements());
        assertEquals(expectationB.getAfterPushingCrossingElements(), adapter.getChainB().getCrossingElements());
    }

    @Test
    @DisplayName("crossing elements: getting when edges are non-equal and there are tails "
            + "and there are three chains")
    public void getCrossingElements_NonEqualEdgesAndTailsThreeChains_True() {
        ChainPushingExpectation expectationA = getChainALongAndTailsExpectation();
        ChainPushingExpectation expectationB = getChainBLongAndTailsExpectation();
        ChainPushingExpectation expectationC = getChainCLongAndTailsExpectation();

        ChainsController<Integer> controller = new SimpleChainsController<>(
                Map.of(
                        "A", expectationA.getStartingChain(),
                        "B", expectationB.getStartingChain(),
                        "C", expectationC.getStartingChain()
                )
        );

        assertEquals(expectationA.getBeforePushingCrossingElements(), controller.getChain("A").getCrossingElements());
        assertEquals(expectationB.getBeforePushingCrossingElements(), controller.getChain("B").getCrossingElements());
        assertEquals(expectationC.getBeforePushingCrossingElements(), controller.getChain("C").getCrossingElements());

        controller.push("A", 0);

        assertEquals(expectationA.getAfterPushingCrossingElements(), controller.getChain("A").getCrossingElements());
        assertEquals(expectationB.getAfterPushingCrossingElements(), controller.getChain("B").getCrossingElements());
        assertEquals(expectationC.getAfterPushingCrossingElements(), controller.getChain("C").getCrossingElements());
    }

    @Test
    @DisplayName("crossing elements: getting when the chain is empty")
    public void getCrossingElements_EmptyChain_True() {
        ChainPushingExpectation expectation = getEmptyChain();

        ChainsController<Integer> controller = new SimpleChainsController<>(
                Map.of(
                        "Empty", expectation.getStartingChain()
                )
        );

        assertEquals(expectation.getBeforePushingCrossingElements(), controller.getChain("Empty").getCrossingElements());

        controller.push("Empty", 0);

        assertEquals(expectation.getAfterPushingCrossingElements(), controller.getChain("Empty").getCrossingElements());
    }
}
