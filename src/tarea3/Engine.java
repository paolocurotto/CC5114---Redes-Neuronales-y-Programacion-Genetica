package tarea3;

public class Engine {

    State state;

    void performActions() {

        Ball ball = state.ball;
        // Set movement for paddles
        for (Individual ind : state.population.individuals) {
            ind.movePaddle(ball);
            ind.paddles.move();
        }
        // Move ball
        ball.move();
        ball.checkCollisionTopEdge();
        ball.checkCollisionBottomEdge();

        if (ball.checkCollisionLeftPaddle() || ball.checkCollisionRightPaddle()) {
            for (Individual ind : state.population.individuals) {
                ind.checkIfHitBall(ball);
                if (ind.lives == 0) {
                    state.individualDied(ind);
                }
            }

        }

    }



}
