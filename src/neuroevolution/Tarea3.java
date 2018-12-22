package neuroevolution;


import java.util.ArrayList;
import java.util.List;

public class Tarea3 {

    public static void main(String[] args) {

        List<A> aaa = new ArrayList<>();
        List<A> bbb = new ArrayList<>();

        A a1 = new A(1);
        A a2 = new A(2);
        A a3 = new A(3);

        aaa.add(a1);
        aaa.add(a2);
        aaa.add(a3);

        for (A a : aaa) {
            if (a.n != 2) {
                bbb.add(a);
            }
        }
        aaa.remove(0);
        aaa.remove(1);

        for (A a : aaa) {
            System.out.println(a.n);
        }
        System.out.println();

        for (A b : bbb) {
            System.out.println(b.n);

        }



    }

    static class A {
        int n;
        A(int n){
            this.n=n;}
    }
}
