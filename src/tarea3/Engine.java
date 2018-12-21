package tarea3;

public class Engine {

    State state;

    void performActions() {

        for (Individual ind : state.population.individuals) {
            ind.movePaddle(state.ball);
        }


    }



}
