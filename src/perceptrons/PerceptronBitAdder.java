package perceptrons;

public class PerceptronBitAdder {
    public PerceptronNAND p1 = new PerceptronNAND();
    public PerceptronNAND p2 = new PerceptronNAND();
    public PerceptronNAND p3 = new PerceptronNAND();
    public PerceptronNAND p4 = new PerceptronNAND();
    public PerceptronNAND p5 = new PerceptronNAND();
    private int sum;
    private int carry;

    public void sum(int x1, int x2) {

        int p1val = p1.eval(x1, x2);
        int p2val = p2.eval(x1, p1val);
        int p3val = p3.eval(p1val, x2);
        int p4val = p4.eval(p1val, p1val);
        int p5val = p5.eval(p2val, p3val);
        sum = p5val;
        carry = p4val;
    }

    public int getSum() {
        return sum;
    }

    public int getCarry() {
        return carry;
    }

}
