package example.com.bouncingBalls_task4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BallCanvas extends JPanel {
    private List<Ball> balls = new ArrayList<>();
    private List<Pocket> pockets = new ArrayList<>();
    private int pocketSize = 30;
    private boolean initialized = false;

    public BallCanvas() {}

    public void add(Ball b) {
        this.balls.add(b);
    }

    private void initializePockets() {
        if(initialized) {return;}
        pockets.add(new Pocket(0, 0, pocketSize));
        pockets.add(new Pocket(getWidth() - pocketSize, 0, pocketSize));
        pockets.add(new Pocket(0, getHeight() - pocketSize, pocketSize));
        pockets.add(new Pocket(getWidth() - pocketSize, getHeight() - pocketSize, pocketSize));
        initialized = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        initializePockets();

        for (Pocket pocket : pockets) {
            pocket.draw(g2);
        }

        for (Ball b : balls) {
            b.draw(g2);
        }
    }

    public List<Pocket> getPockets() {
        return pockets;
    }

    public int getPocketSize() {return pocketSize;}
}
