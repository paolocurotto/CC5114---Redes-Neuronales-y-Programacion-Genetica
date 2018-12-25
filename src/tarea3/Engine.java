package tarea3;

import java.util.ListIterator;

class Engine {

    State state;

    synchronized void performActions() {
        // Set movement for paddles
        Ball ball = state.ball;
        for (Individual individual : state.population.individuals) {
            individual.setNextMove(ball);
            individual.movePaddle();
        }
        // Move ball
        ball.move();
        ball.checkCollisionTopEdge();
        ball.checkCollisionBottomEdge();

        // Ball in position to get hit by paddles
        if (ball.checkCollisionLeftPaddle() || ball.checkCollisionRightPaddle()) {
            // Check which individuals hit the ball
            boolean atLeast1Hit = false;
            ListIterator<Individual> iterator = state.current_individuals.listIterator();
            while (iterator.hasNext()) {
                Individual individual = iterator.next();
                if (individual.checkIfHitBall(ball)) {
                    state.individualHitBall(individual);
                    atLeast1Hit = true;
                } else {
                    state.individualMissedBall(iterator, individual);
                }
            }
            if (atLeast1Hit) {
                state.ballHitSomePaddle();
                ball.changeXDirection();
            } else {
                state.allPaddlesMissedBall();
            }
        }

        if (ball.checkCollisionLeftEdge() || ball.checkCollisionRightEdge()) {
            state.ballHitBorder();
        }

    }



}
