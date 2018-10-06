
public class Main {

    public static void main(String[] args) {

        Tests t = new Tests();

        // Test OR, AND, NAND
        t.logicTests();

        // Test bit adder
        t.sumTests();

        System.out.println("All tests passed");
    }
}
