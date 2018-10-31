package perceptrons;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class Tests {

    @Test
    public void logicTests() {

        Perceptron or = new PerceptronOR();
        Perceptron and = new PerceptronAND();
        Perceptron nand = new PerceptronNAND();

        // OR
        assertEquals(0, or.eval(0, 0));
        assertEquals(1, or.eval(0, 1));
        assertEquals(1, or.eval(1, 0));
        assertEquals(1, or.eval(1, 1));

        // AND
        assertEquals(0, and.eval(0, 0));
        assertEquals(0, and.eval(0, 1));
        assertEquals(0, and.eval(1, 0));
        assertEquals(1, and.eval(1, 1));

        // NAND
        assertEquals(1, nand.eval(0, 0));
        assertEquals(1, nand.eval(0, 1));
        assertEquals(1, nand.eval(1, 0));
        assertEquals(0, nand.eval(1, 1));
    }

    @Test
    public void sumTests() {

        PerceptronBitAdder adder = new PerceptronBitAdder();

        // 0 + 0
        adder.sum(0, 0);
        assertEquals(0, adder.getSum());
        assertEquals(0, adder.getCarry());

        // 0 + 1
        adder.sum(0, 1);
        assertEquals(1, adder.getSum());
        assertEquals(0, adder.getCarry());

        // 1 + 0
        adder.sum(1, 0);
        assertEquals(1, adder.getSum());
        assertEquals(0, adder.getCarry());

        // 1 + 1
        adder.sum(1, 1);
        assertEquals(0, adder.getSum());
        assertEquals(1, adder.getCarry());
    }

}
