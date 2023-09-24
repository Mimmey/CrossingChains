import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mimmey.adapter.ChainsControllerAdapter;
import org.mimmey.adapter.SimpleChainsControllerAdapter;
import org.mimmey.controller.ChainsController;
import org.mimmey.controller.SimpleChainsController;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChainValuesTest extends ChainAbstractTest {

    private ChainsControllerAdapter adapter;

    @Test
    @DisplayName("chain values: pushing when all edges are equal and there are no tails")
    public void push_EqualEdgesNoTails_True() {
        ChainPushingExpectation expectationA = getChainAExpectation();
        ChainPushingExpectation expectationB = getChainBExpectation();

        adapter = new SimpleChainsControllerAdapter(
                expectationA.getStartingChain(),
                expectationB.getStartingChain()
        );

        int popping = adapter.pushA(0);

        assertEquals(1, popping);
        assertEquals(expectationA.getAfterPushingChainValues(), adapter.getChainA().getValues());
        assertEquals(expectationB.getAfterPushingChainValues(), adapter.getChainB().getValues());
    }

    @Test
    @DisplayName("chain values: pushing when edges are non-equal and there are not tails")
    public void push_NonEqualEdgesNoTails_True() {
        ChainPushingExpectation expectationA = getChainAExpectation();
        ChainPushingExpectation expectationB = getChainBLongExpectation();

        adapter = new SimpleChainsControllerAdapter(
                expectationA.getStartingChain(),
                expectationB.getStartingChain()
        );

        int popping = adapter.pushA(0);

        assertEquals(1, popping);
        assertEquals(expectationA.getAfterPushingChainValues(), adapter.getChainA().getValues());
        assertEquals(expectationB.getAfterPushingChainValues(), adapter.getChainB().getValues());
    }


    @Test
    @DisplayName("chain values: pushing when edges are non-equal and there are tails")
    public void push_NonEqualEdgesAndTails_True() {
        ChainPushingExpectation expectationA = getChainALongAndTailsExpectation();
        ChainPushingExpectation expectationB = getChainBLongAndTailsExpectation();

        adapter = new SimpleChainsControllerAdapter(
                expectationA.getStartingChain(),
                expectationB.getStartingChain()
        );

        int popping = adapter.pushA(0);

        assertEquals(-1, popping);
        assertEquals(expectationA.getAfterPushingChainValues(), adapter.getChainA().getValues());
        assertEquals(expectationB.getAfterPushingChainValues(), adapter.getChainB().getValues());
    }

    @Test
    @DisplayName("chain values: pushing when edges are non-equal and there are tails "
            + "and there are three chains")
    public void push_NonEqualEdgesAndTailsThreeChains_True() {
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

        int popping = controller.push("A", 0);

        assertEquals(-1, popping);
        assertEquals(expectationA.getAfterPushingChainValues(), controller.getChain("A").getValues());
        assertEquals(expectationB.getAfterPushingChainValues(), controller.getChain("B").getValues());
        assertEquals(expectationC.getAfterPushingChainValues(), controller.getChain("C").getValues());
    }

    @Test
    @DisplayName("chain values: pushing when the chain is empty")
    public void push_EmptyChain_True() {
        ChainPushingExpectation expectation = getEmptyChain();

        ChainsController<Integer> controller = new SimpleChainsController<>(
                Map.of(
                        "Empty", expectation.getStartingChain()
                )
        );

        int popping = controller.push("Empty", 0);

        assertEquals(0, popping);
        assertEquals(expectation.getAfterPushingChainValues(), controller.getChain("Empty").getValues());
    }
}
