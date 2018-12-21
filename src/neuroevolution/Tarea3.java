package neuroevolution;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static neuroevolution.pong.Options.*;

public class Tarea3 {

    public static void main(String[] args) {

        double[] info = new double[]{200, 500, 300, -3.4, 3};
        List<Double> outputs = Arrays.stream(info).boxed().collect(Collectors.toList());
        // Normalize

        for (double a : outputs)
            System.out.println(a);

        System.out.println();

        double dL = -BALL_SPEED;
        double dH = WINDOW_WIDTH;
        double nL = 0;
        double nH = 1;
        //d = (double) (d - dL) / (dH - dL);

        double max_vy = BALL_SPEED*0.865;
        double min_vy = -max_vy;
        double max_vx = BALL_SPEED*0.939;
        double min_vx = -max_vx;
        outputs.set(0, (outputs.get(0) - PADDLE_HEIGHT/2.0) / ((WINDOW_HEIGHT - PADDLE_HEIGHT/2.0) - PADDLE_HEIGHT/2.0));
        outputs.set(1, (outputs.get(1) - 0) / (WINDOW_WIDTH - 0));
        outputs.set(2, (outputs.get(2) - 0) / (WINDOW_HEIGHT - 0));
        outputs.set(3, (outputs.get(3) - min_vx) / (max_vx - min_vx));
        outputs.set(4, (outputs.get(4) - min_vy) / (max_vy - min_vy));

        for (double a : outputs)
            System.out.println(a);
    }
}
