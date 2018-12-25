package neuroevolution;


import tarea3.Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Tarea3 {

    public static void main(String[] args) {

        List<A> aaa = new ArrayList<>();
        List<A> bbb = new ArrayList<>();


        A a1 = new A(0.2);
        A a2 = new A(0.1);
        A a3 = new A(0.3);
        A a4 = new A(0.2);

        aaa.add(a1);
        aaa.add(a2);
        aaa.add(a3);
        aaa.add(a4);

        aaa.forEach(a -> System.out.println(a.n));
        System.out.println();

        aaa.sort((a, b) -> (b.n - a.n > 0) ? 1 : (b.n < a.n) ? -1 : 0);


        aaa.forEach(a -> System.out.println(a.n));





    }

    static class A {
        double n;
        A(double n){
            this.n=n;}
    }
}
