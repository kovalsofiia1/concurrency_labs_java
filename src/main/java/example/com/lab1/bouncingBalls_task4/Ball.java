package example.com.lab1.bouncingBalls_task4;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Random;

public class Ball {
    private Component canvas;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private int x = 0;
    private int y = 0;
    private int dx = 2;
    private int dy = 2;
    private boolean running = true;
    private List<Pocket> pockets; // Лунки
    private Color color;

    public Ball(Component c, List<Pocket> pockets, int pocketSize, Color color) {
        this.canvas = c;
        this.pockets = pockets;
        this.color = color;

        if (Math.random() < 0.5) {
            x = new Random().nextInt(pocketSize+XSIZE, this.canvas.getWidth() - pocketSize - XSIZE);
            y = 0;
        } else {
            x = 0;
            y = new Random().nextInt(pocketSize+YSIZE, this.canvas.getHeight() - pocketSize-YSIZE);
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void draw(Graphics2D g2) {
        if (running) {
            g2.setColor(color);
            g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
        }
    }

    public void move() {
        if (!running) return;

        x += dx;
        y += dy;

        if (x < 0) {
            x = 0;
            dx = -dx;
        }
        if (x + XSIZE >= this.canvas.getWidth()) {
            x = this.canvas.getWidth() - XSIZE;
            dx = -dx;
        }
        if (y < 0) {
            y = 0;
            dy = -dy;
        }
        if (y + YSIZE >= this.canvas.getHeight()) {
            y = this.canvas.getHeight() - YSIZE;
            dy = -dy;
        }

        for (Pocket pocket : pockets) {
            if (pocket.contains(x, y, XSIZE)) {
                running = false; // Кулька зникла
                return;
            }
        }

        this.canvas.repaint();
    }
}


