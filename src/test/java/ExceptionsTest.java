import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mimmey.adapter.SimpleChainsControllerAdapter;
import org.mimmey.chain.SimpleChain;
import org.mimmey.controller.ChainsController;
import org.mimmey.controller.SimpleChainsController;
import org.mimmey.exception.NoSuchChainException;
import org.mimmey.exception.validation.chain.IllegalEdgesException;
import org.mimmey.exception.validation.chain.TooFewElementsException;
import org.mimmey.exception.validation.controller.IllegalControllerChangesException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionsTest extends ChainAbstractTest {

    @Test
    @DisplayName("exceptions: pushing when no such chain")
    public void push_NoSuchChain_Throws() {
        ChainPushingExpectation expectationA = getChainALongAndTailsExpectation();
        ChainPushingExpectation expectationB = getChainBLongAndTailsExpectation();
        ChainPushingExpectation expectationC = getChainCLongAndTailsExpectation();

        SimpleChain<Integer> chainA = expectationA.getStartingChain();
        SimpleChain<Integer> chainB = expectationB.getStartingChain();
        SimpleChain<Integer> chainC = expectationC.getStartingChain();

        ChainsController<Integer> controller = new SimpleChainsController<>(
                Map.of(
                        "A", expectationA.getStartingChain(),
                        "B", expectationB.getStartingChain(),
                        "C", expectationC.getStartingChain()
                )
        );

        assertThrows(NoSuchChainException.class, () -> controller.push("D", 0));
        assertEquals(expectationA.getStartingChain().getValues(), chainA.getValues());
        assertEquals(expectationB.getStartingChain().getValues(), chainB.getValues());
        assertEquals(expectationC.getStartingChain().getValues(), chainC.getValues());
    }

    @Test
    @DisplayName("exceptions: creating new chain when there are too few elements for given edges")
    public void new_TooFewElements_Throws() {
        assertThrows(TooFewElementsException.class, this::getChainATooFewElements);
    }

    @Test
    @DisplayName("exceptions: creating new chain when some edges are illegal")
    public void new_IllegalEdges_Throws() {
        assertThrows(IllegalEdgesException.class, this::getChainANegativeEdges);
    }

    @Test
    @DisplayName("exceptions: creating new controller when chains in controller have different crossing elements counts")
    public void new_IllegalController_Throws() {
        ChainPushingExpectation expectationA = getChainAExpectation();

        SimpleChain<Integer> chainA = expectationA.getStartingChain();
        SimpleChain<Integer> chainB = getChainBTooMuchEdges();

        assertThrows(IllegalControllerChangesException.class,
                () -> new SimpleChainsController<>(
                        Map.of(
                                "A", chainA,
                                "B", chainB
                        )
                ));
    }

    @Test
    @DisplayName("exceptions: creating new adapter when chains in its controller have different crossing elements counts")
    public void new_IllegalAdapter_Throws() {
        ChainPushingExpectation expectationA = getChainAExpectation();

        SimpleChain<Integer> chainA = expectationA.getStartingChain();
        SimpleChain<Integer> chainB = getChainBTooMuchEdges();

        assertThrows(IllegalControllerChangesException.class,
                () -> new SimpleChainsControllerAdapter(chainA, chainB));
    }
}
