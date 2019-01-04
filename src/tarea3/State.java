package tarea3;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static tarea3.Globals.*;

public class State {
    int playtime;
    Ball ball;
    List<Individual> current_individuals;
    private Population population;

    State() {
        playtime = PLAYTIME;
        current_individuals = new ArrayList<>();
        ball = new Ball();
        population = new Population();
        current_individuals.addAll(population.individuals);
    }

    synchronized void performActions() {
        // Move paddles
        for (Individual individual : population.individuals) {
            individual.setNextMove(ball); // -> Neural network
            individual.movePaddle();
        }
        // Move ball
        ball.move();
        ball.checkCollisionTopEdge();
        ball.checkCollisionBottomEdge();

        // Ball in position to get hit by paddles
        if (ball.checkPositionToGetHitLeft() || ball.checkPositionToGetHitRight()) {
            // Check which individuals hit the ball
            boolean atLeast1Hit = false;
            ListIterator<Individual> iterator = current_individuals.listIterator();
            while (iterator.hasNext()) {
                Individual individual = iterator.next();
                if (individual.checkIfHitBall(ball)) {
                    atLeast1Hit = true;
                } else {
                    individualMissedBall(iterator, individual);
                }
            }
            if (atLeast1Hit) {
                ballHitSomePaddle();
                changeBallXDirection();
            } else {
                allPaddlesMissedBall();
            }
        }
        // Ball reached left or right edge
        if (ball.checkCollisionLeftEdge() || ball.checkCollisionRightEdge()) {
            ballHitBorder();
        }
    }

    private void ballHitSomePaddle() {
        playtime--;
        if (playtime == 0) {
            resetGame();
        }
    }

    private void allPaddlesMissedBall() {
        ball.active = false;
    }

    private void ballHitBorder() {
        ball.active = true;
        ball.resetBall();
        if (current_individuals.size() == 0) {
            resetGame();
        }
    }

    private synchronized void individualMissedBall(ListIterator<Individual> iterator, Individual individual) {
        individual.lives--;
        if (individual.lives == 0) {
            iterator.remove();
        }
    }

    private void changeBallXDirection() {
        ball.vx = -ball.vx;
    }

    synchronized private void resetGame() {
        playtime = PLAYTIME;
        ball.resetBall();
        // Genetic algorithm
        population.startNewGeneration();
        current_individuals.clear();
        current_individuals.addAll(population.individuals);
    }
}
