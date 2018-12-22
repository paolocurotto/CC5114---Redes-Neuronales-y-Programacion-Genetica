package tarea3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static tarea3.Globals.*;

public class State {

    int playballs;
    int playtime;
    List<Paddles> pads;
    List<Paddles> current_pads;
    Ball ball;
    Population population;

    State() {
        playballs = PLAYBALLS;
        playtime = PLAYTIME;
        pads = new ArrayList<>();
        current_pads = new ArrayList<>();
        ball = new Ball();
        population = new Population();
        IntStream.range(0, POPULATION_SIZE).forEach(i -> pads.add(new Paddles()));
        IntStream.range(0, POPULATION_SIZE).forEach(i -> population.individuals.get(i).paddles = pads.get(i));
        current_pads.addAll(pads);
    }

    void individualDied(Individual ind) {

    }
}
