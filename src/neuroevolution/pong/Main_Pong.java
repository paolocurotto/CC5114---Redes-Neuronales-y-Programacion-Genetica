package neuroevolution.pong;

import javax.swing.*;
import java.awt.*;

public class Main_Pong extends JFrame implements Options {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main_Pong game = new Main_Pong();
            game.setVisible(true);
        });
    }

    public Main_Pong() {
        add(new Game());
        setTitle("Pong ai");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH + 15, WINDOW_HEIGHT + 39);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

}
