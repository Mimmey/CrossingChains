package org.mimmey;

import org.mimmey.adapter.ChainsControllerAdapter;
import org.mimmey.adapter.SimpleChainsControllerAdapter;
import org.mimmey.chain.Chain;
import org.mimmey.chain.SimpleChain;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Chain<Integer> chainA = new SimpleChain<>(
                5,
                List.of(4, 5),
                List.of(2, 3, 5, 7, 9,
                        11, 13, 17, 19, 23,
                        29, 31, 37, 41, 43, 47,
                        53, 59, 61, 67, 71, 73, 79, 83),
                "A");
        Chain<Integer> chainB = new SimpleChain<>(
                2,
                List.of(1, 2),
                List.of(89, 97,
                        11, 103,
                        29, 109, 113,
                        53, 131, 137),
                "B");

        ChainsControllerAdapter adapter = new SimpleChainsControllerAdapter(chainA, chainB);

        System.out.println(adapter.pushA(5));
        System.out.println(chainA.getValues());
        System.out.println(chainB.getValues());
        System.out.println(adapter.getChainA().getValues());
        System.out.println(adapter.getChainB().getValues());
    }
}