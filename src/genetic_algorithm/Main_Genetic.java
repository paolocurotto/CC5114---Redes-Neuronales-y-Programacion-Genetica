package genetic_algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class Main_Genetic {

    public static void main(String[] args) {

        List<Integer> nums = new ArrayList<>();
        IntStream.range(0, 10).forEach(nums::add);

        Iterator<Integer> it = nums.iterator();


        Integer integer = it.next();
        System.out.println(integer);

        nums.remove(Integer.valueOf(1));

        integer = it.next();
        System.out.println(integer);



    }

    static class Hola {

    }
}
