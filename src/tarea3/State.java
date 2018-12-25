package tarea3;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static tarea3.Globals.*;

public class State {

    int playballs;
    int playtime;
    Ball ball;
    List<Individual> current_individuals;
    Population population;

    State() {
        playballs = PLAYBALLS;
        playtime = PLAYTIME;
        current_individuals = new ArrayList<>();
        ball = new Ball();
        population = new Population();
        current_individuals.addAll(population.individuals);
    }

    void ballHitSomePaddle() {
        //ball.active = false;
        playtime--;
        if (playtime == 0) {
            resetGame();
        }
    }

    void allPaddlesMissedBall() {
        ball.active = false;

    }

    void ballHitBorder() {
        ball.active = true;
        playballs--;
        ball.resetBall();
        if (playballs == 0 || current_individuals.size() == 0) {
            resetGame();
        }
    }

    void individualHitBall(Individual individual) {
        individual.hits++;
    }

    synchronized void individualMissedBall(ListIterator<Individual> iterator, Individual individual) {
        individual.lives--;
        if (individual.lives == 0) {
            iterator.remove();
        }
    }

    synchronized private void resetGame() {
        playballs = PLAYBALLS;
        playtime = PLAYTIME;
        ball.resetBall();
        population.startNewGeneration();
        current_individuals.clear();
        current_individuals.addAll(population.individuals);
    }
}
