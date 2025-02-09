package example.com.bouncingBalls_task3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Color> colors = new ArrayList<>();

    public void add(Ball b, Color color) {
        this.balls.add(b);
        this.colors.add(color);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).draw(g2, colors.get(i));
        }
    }
}
