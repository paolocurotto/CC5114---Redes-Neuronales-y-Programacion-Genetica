package tarea3;

import javax.swing.*;
import java.awt.*;

import static tarea3.Globals.*;

public class Main_Tarea3 extends JFrame {

    public static void main(String[] args) {
        EventQueue.invokeLater(Main_Tarea3::run);
    }

    private Main_Tarea3() {
        add(new Pong());
        setTitle("Pong ai");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH + 15, WINDOW_HEIGHT + 39);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    private static void run() {
        Main_Tarea3 game = new Main_Tarea3();
        game.setVisible(true);
    }
}
