public class PerceptronAND extends Perceptron {

    public PerceptronAND() {
        super(new int[] {2, 2}, 3);
    }

    public int eval(int x1, int x2) {
        inputs[0] = x1;
        inputs[1] = x2;
        return super.calc();
    }

}
