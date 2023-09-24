import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mimmey.adapter.ChainsControllerAdapter;
import org.mimmey.adapter.PrimeIntegersChainsControllerAdapter;
import org.mimmey.exception.validation.NumberIsNotPrimeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrimeNumbersTest extends ChainAbstractTest {

    private ChainsControllerAdapter adapter;

    @Test
    @DisplayName("prime chains: pushing prime number")
    public void push_Primes_True() {
        ChainPushingExpectation expectationA = getChainAPrimeExpectation();
        ChainPushingExpectation expectationB = getChainBPrimeExpectation();

        adapter = new PrimeIntegersChainsControllerAdapter(
                expectationA.getStartingChain(),
                expectationB.getStartingChain()
        );

        int popping = adapter.pushA(11);

        assertEquals(2, popping);
        assertEquals(expectationA.getAfterPushingChainValues(), adapter.getChainA().getValues());
        assertEquals(expectationB.getAfterPushingChainValues(), adapter.getChainB().getValues());
    }

    @Test
    @DisplayName("prime chains: initialization with not prime chains")
    public void new_ComplexNumbersInChains_Throws() {
        ChainPushingExpectation expectationA = getChainAExpectation();
        ChainPushingExpectation expectationB = getChainBExpectation();

        assertThrows(
                NumberIsNotPrimeException.class,
                () -> new PrimeIntegersChainsControllerAdapter(
                        expectationA.getStartingChain(),
                        expectationB.getStartingChain()
                )
        );
    }

    @Test
    @DisplayName("prime chains: pushing complex number")
    public void push_ComplexNumber_Throws() {
        ChainPushingExpectation expectationA = getChainAPrimeExpectation();
        ChainPushingExpectation expectationB = getChainBPrimeExpectation();

        adapter = new PrimeIntegersChainsControllerAdapter(
                expectationA.getStartingChain(),
                expectationB.getStartingChain()
        );

        assertThrows(NumberIsNotPrimeException.class, () -> adapter.pushA(25));
    }
}
